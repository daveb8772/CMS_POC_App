#!/bin/zsh
# file: 4.4-deploy-monitoring.sh

set -x
#Local helm directory
LOCAL_INSTALL_HELM_PATH=""../k3s""
LOCAL_HELM_PATH=""../k3s/prometheus-setup""
# Variables for remote server connection
REMOTE_USER="super"
#REMOTE_SERVER="172.16.79.128"
REMOTE_SERVER="192.168.2.173"
REMOTE_PORT="2222"
KUBECONFIG_PATH="/home/super/Documents/Software/CMS/yaml/k3s.yaml"
REMOTE_APP_COPY_PATH="/home/super/Documents/Software/CMS"
REMOTE_CHART_PATH="/home/super/Documents/Software/CMS/prometheus-setup"

# Function to execute SSH commands
ssh_exec() {
    ssh -p "$REMOTE_PORT" "$REMOTE_USER@$REMOTE_SERVER" "$1"
}


# Check if Helm is installed and install if not
CHECK_HELM_INSTALLED='helm version --short > /dev/null 2>&1 || (echo "Helm not found, installing..." && curl https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3 | bash)'
ssh_exec "$CHECK_HELM_INSTALLED"


# Add the Prometheus Helm chart repository and update
echo "Adding Prometheus Helm chart repository..."
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo update

# Fetch the latest version of Prometheus chart to the local helm directory
echo "Fetching the latest Prometheus Helm chart..."
helm fetch prometheus-community/prometheus --untar --untardir $LOCAL_INSTALL_HELM_PATH
mv ../k3s/prometheus ../k3s/prometheus-setup

# Copy Prometheus setup files to the remote server
echo "Copying Prometheus setup files to the remote server..."
scp -P "$REMOTE_PORT" -r $LOCAL_HELM_PATH "$REMOTE_USER@$REMOTE_SERVER:$REMOTE_APP_COPY_PATH"


# Apply Helm chart for Prometheus
APPLY_PROMETHEUS_CHART="helm upgrade --install prometheus-setup $REMOTE_CHART_PATH --namespace monitoring --create-namespace --kubeconfig $KUBECONFIG_PATH"
ssh_exec "$APPLY_PROMETHEUS_CHART"

# Check the status of the Helm release
CHECK_HELM_RELEASE_STATUS="helm status prometheus-setup --namespace monitoring --kubeconfig $KUBECONFIG_PATH"
ssh_exec "$CHECK_HELM_RELEASE_STATUS"

# Get all resources in the monitoring namespace to verify the installation
GET_RESOURCES="kubectl --kubeconfig=${KUBECONFIG_PATH} get all -n monitoring"
ssh_exec "$GET_RESOURCES"

# Optionally, if you want to set up port forwarding to access Prometheus dashboard
# This command will run in the foreground, to run in the background, you will need to handle job control
# PORT_FORWARD_COMMAND="kubectl --kubeconfig=${KUBECONFIG_PATH} port-forward svc/prometheus-server 9090:9090 -n monitoring"
# ssh_exec "$PORT_FORWARD_COMMAND"
