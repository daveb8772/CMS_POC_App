#!/bin/bash
set -x

# Variables for remote server connection
REMOTE_USER="super"
REMOTE_HOST="super"  # Use your server's hostname or IP address
REMOTE_PORT="2222"
# Define remote directories
REMOTE_BASE_DIR="/home/super/Documents/Software/CMS"
REMOTE_APP_DIR="$REMOTE_BASE_DIR/app"
REMOTE_YAML_DIR="$REMOTE_BASE_DIR/yaml"
KUBECONFIG_PATH="$REMOTE_YAML_DIR/k3s.yaml"  # Path to K3s kubeconfig on remote server

# Define variables
REGISTRY_PORT="5000"
REGISTRY_HOST_ALIAS="myregistry.local"  # Alias for the registry
REGISTRY_ADDRESS="$REGISTRY_HOST_ALIAS:$REGISTRY_PORT"
REGISTRIES_CONFIG_PATH="/etc/rancher/k3s/registries.yaml"
DOCKER_USERNAME="super"
DOCKER_EMAIL="daveb8772@gmail.com"
DOCKER_PASSWORD_ENV_VAR="DOCKER_REGISTRY_PASSWORD"  # Name of the environment variable storing the Docker registry password on the remote server

# Commands to execute on the remote server
REMOTE_COMMANDS=$(cat <<EOF
# Load KUBECONFIG
export KUBECONFIG=$KUBECONFIG_PATH

# Ensure the registries.yaml configuration directory exists
if [ ! -d "\$(dirname $REGISTRIES_CONFIG_PATH)" ]; then
    echo "Creating configuration directory..."
    sudo mkdir -p "\$(dirname $REGISTRIES_CONFIG_PATH)"
else
    echo "Configuration directory already exists."
fi

# Configure K3s to use local Docker registry
echo "Configuring K3s to use local Docker registry..."
cat <<EOT | sudo tee $REGISTRIES_CONFIG_PATH > /dev/null
mirrors:
  $REGISTRY_HOST_ALIAS:
    endpoint:
      - "http://$REGISTRY_ADDRESS"
EOT

# Add registry alias to /etc/hosts to resolve to 127.0.0.1
echo "Adding registry address alias to /etc/hosts..."
echo "127.0.0.1 $REGISTRY_HOST_ALIAS" | sudo tee -a /etc/hosts > /dev/null

# Add registry alias to .zshrc for easy use
echo "Adding registry alias to .zshrc..."
echo "alias docker-registry='docker login $REGISTRY_ADDRESS'" | tee -a \$HOME/.zshrc > /dev/null

# Check if the Kubernetes Docker registry secret exists, if not, create it using the password stored in the environment variable
echo "Checking and creating Docker registry secret if necessary..."
if ! kubectl get secret myregistrysecret > /dev/null 2>&1; then
    kubectl create secret docker-registry myregistrysecret --docker-server=$REGISTRY_HOST_ALIAS --docker-username=$DOCKER_USERNAME --docker-password=\$$DOCKER_PASSWORD_ENV_VAR --docker-email=$DOCKER_EMAIL
fi

# Restart K3s to apply registry configuration
echo "Restarting K3s to apply configurations..."
sudo systemctl daemon-reload
sudo systemctl restart k3s

echo "Setup completed. Please restart your shell or source .zshrc to use the new alias."
EOF
)

# Execute the commands on the remote server
ssh -t -p "$REMOTE_PORT" "$REMOTE_USER@$REMOTE_HOST" "$REMOTE_COMMANDS"
