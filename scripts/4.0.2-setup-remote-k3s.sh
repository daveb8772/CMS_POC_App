#!/bin/bash
#set -x for debugging

# Variables for remote server connection
REMOTE_USER="super"
#REMOTE_HOST="172.16.79.128"  # Use your server's hostname or IP address
REMOTE_HOST="192.168.2.173"  # Use your server's hostname or IP address
REMOTE_PORT="2222"
REMOTE_K3S_DIR="/etc/rancher/k3s"
REMOTE_BASE_DIR="/home/super/Documents/Software/CMS"

# SSH command function to avoid repetition
ssh_exec() {
    ssh -t -p "$REMOTE_PORT" "$REMOTE_USER@$REMOTE_HOST" "$1"
}
echo "*************** Setup Remote Script at REMOTE_HOST=$REMOTE_HOST ****************"

echo "Command to check if App directories exist before creating them"
# Command to check if App, yaml, and Prometheus setup directories exist before creating them
CHECK_AND_CREATE_DIRECTORIES='mkdir -p /home/super/Documents/Software/CMS/{app,yaml,prometheus-setup}'
ssh_exec "[[ -d /home/super/Documents/Software/CMS/app && -d /home/super/Documents/Software/CMS/yaml && -d /home/super/Documents/Software/CMS/prometheus-setup ]] || $CHECK_AND_CREATE_DIRECTORIES"

echo "Command to check if k3s.yaml exists before copying to App path"
# Command to check if k3s.yaml exists before copying
CHECK_AND_COPY_KUBECONFIG='[ -f /etc/rancher/k3s/k3s.yaml ] && cd /home/super/Documents/Software/CMS/yaml && sudo cp /etc/rancher/k3s/k3s.yaml .'
ssh_exec "$CHECK_AND_COPY_KUBECONFIG"

echo "Add myregistry.local to /etc/hosts if not present"
# Add myregistry.local to /etc/hosts if not present
ADD_REGISTRY_HOST_COMMAND='grep -q 'myregistry.local' /etc/hosts || echo "$REMOTE_HOST myregistry.local" | sudo tee -a /etc/hosts'
ssh_exec "$ADD_REGISTRY_HOST_COMMAND"

echo "Command to change permissions of k3s.yaml and export KUBECONFIG if the file was copied"
# Command to change permissions of k3s.yaml and export KUBECONFIG if the file was copied
CHANGE_PERMISSIONS='[ -f /home/super/Documents/Software/CMS/yaml/k3s.yaml ] && sudo chmod +r /home/super/Documents/Software/CMS/yaml/k3s.yaml'
ssh_exec "$CHANGE_PERMISSIONS"

echo "Load KUBECONFIG"
# Load KUBECONFIG
KUBECONFIG_EXPORT='export KUBECONFIG=/home/super/Documents/Software/CMS/yaml/k3s.yaml'
ssh_exec "$KUBECONFIG_EXPORT"

# Install Helm on the remote server if it's not already installed
echo "Install Helm on the remote server if it's not already installed"
CHECK_HELM_INSTALLED="helm > /dev/null 2>&1 || { echo >&2 'Helm not installed. Installing...'; curl https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3 | bash; }"
ssh_exec "$CHECK_HELM_INSTALLED"

echo "Fetch the primary IP of the K3s node and map myapp.local domain to K3s Node IP in /etc/hosts"
# Fetch the primary IP of the K3s node and map myapp.local domain to K3s Node IP in /etc/hosts
FETCH_AND_MAP_IP_COMMAND='K3S_NODE_IP=$(ip route get 1.2.3.4 | awk "{print \$7}") && echo "K3s Node IP is: $K3S_NODE_IP" && echo "$K3S_NODE_IP myapp.local" | sudo tee -a /etc/hosts > /dev/null'
ssh_exec "$FETCH_AND_MAP_IP_COMMAND"

echo "Ensure the registries.yaml configuration directory exists"
# Ensure the registries.yaml configuration directory exists
ENSURE_DIR_COMMAND='[ ! -d "$(dirname /etc/rancher/k3s/registries.yaml)" ] && sudo mkdir -p "$(dirname /etc/rancher/k3s/registries.yaml)" || echo "Configuration directory already exists."'
ssh_exec "$ENSURE_DIR_COMMAND"

echo "Configure K3s to use local Docker registry"
# Configure K3s to use local Docker registry
CONFIGURE_REGISTRY_COMMAND='echo "Configuring K3s to use local Docker registry..." && cat <<EOT | sudo tee /etc/rancher/k3s/registries.yaml > /dev/null
mirrors:
  myregistry.local:
    endpoint:
      - "http://myregistry.local:5000"
      - "https://myregistry.local:5000"
EOT'
ssh_exec "$CONFIGURE_REGISTRY_COMMAND"

#echo "Add registry alias to /etc/hosts and .zshrc"
# Add registry alias to /etc/hosts and .zshrc
#ADD_ALIAS_COMMAND='echo "127.0.0.1 myregistry.local" | sudo tee -a /etc/hosts > /dev/null && echo "alias docker-registry=\"docker login myregistry.local:5000\"" | tee -a $HOME/.zshrc > /dev/null'
#ssh_exec "$ADD_ALIAS_COMMAND"

echo "Retrieve Docker registry password securely and create Kubernetes Docker registry secret if it doesn't exist"
# Retrieve Docker registry password securely and create Kubernetes Docker registry secret if it doesn't exist
CREATE_DOCKER_REGISTRY_SECRET_COMMAND='source ~/.zshrc && kubectl get secret myregistrysecret > /dev/null 2>&1 || kubectl create secret docker-registry myregistrysecret --docker-server=myregistry.local:5000 --docker-username=super --docker-password="$DOCKER_REGISTRY_PASSWORD" --docker-email=daveb8772@gmail.com'
ssh_exec "$CREATE_DOCKER_REGISTRY_SECRET_COMMAND"

echo "Retrieve DB password securely and create Kubernetes DB secret if it doesn't exist"
# Retrieve DB password securely and create Kubernetes DB secret if it doesn't exist
CREATE_DB_SECRET_COMMAND='source ~/.zshrc && kubectl get secret myapp-db-secret > /dev/null 2>&1 || kubectl create secret generic myapp-db-secret --from-literal=db-password="$DB_PASSWORD"'
ssh_exec "$CREATE_DB_SECRET_COMMAND"

echo "Restart K3s to apply registry configuration"
# Restart K3s to apply registry configuration
RESTART_K3S_COMMAND='sudo systemctl daemon-reload && sudo systemctl restart k3s && echo "Setup completed. Please restart your shell or source .zshrc to use the new alias."'
ssh_exec "$RESTART_K3S_COMMAND"
