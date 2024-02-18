#!/bin/zsh
# file: 4.5-deploy-metrics-server.sh

set -x
# Local Helm directory
LOCAL_INSTALL_HELM_PATH="../k3s"
LOCAL_HELM_PATH="../k3s/metrics-server-setup"
# Variables for remote server connection
REMOTE_USER="super"
REMOTE_SERVER="192.168.2.173"
REMOTE_PORT="2222"
KUBECONFIG_PATH="/home/super/Documents/Software/CMS/yaml/k3s.yaml"
REMOTE_APP_COPY_PATH="/home/super/Documents/Software/CMS"
REMOTE_CHART_PATH="/home/super/Documents/Software/CMS/metrics-server-setup"

# Function to execute SSH commands
ssh_exec() {
    ssh -p "$REMOTE_PORT" "$REMOTE_USER@$REMOTE_SERVER" "$1"
}

# Check if Helm is installed and install if not
echo "Step 1 - Check if Helm is installed and install if not"
CHECK_HELM_INSTALLED='helm version --short > /dev/null 2>&1 || (echo "Helm not found, installing..." && curl https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3 | bash)'
ssh_exec "$CHECK_HELM_INSTALLED"

# Add the Metrics Server Helm chart repository and update
echo "Step 2 - Adding Metrics Server Helm chart repository..."
helm repo add metrics-server https://kubernetes-sigs.github.io/metrics-server/
helm repo update

# Fetch the latest version of Metrics Server chart to the local helm directory
echo "Step 3a - Fetching the latest Metrics Server Helm chart..."
helm fetch metrics-server/metrics-server --untar --untardir $LOCAL_INSTALL_HELM_PATH

# Copy Metrics Server setup files to the remote server
echo "Step 3b - Copying Metrics Server setup files to the remote server..."
scp -P "$REMOTE_PORT" -r $LOCAL_HELM_PATH "$REMOTE_USER@$REMOTE_SERVER:$REMOTE_APP_COPY_PATH"

# Apply Helm chart for Metrics Server
echo "Step 4 - Apply Helm chart for Metrics Server "
APPLY_METRICS_SERVER_CHART="helm upgrade --install metrics-server $REMOTE_CHART_PATH --namespace kube-system --create-namespace --kubeconfig $KUBECONFIG_PATH"
ssh_exec "$APPLY_METRICS_SERVER_CHART"

# Check the status of the Helm release
echo "Step 5 - Check the status of the Helm release"
CHECK_HELM_RELEASE_STATUS="helm status metrics-server --namespace kube-system --kubeconfig $KUBECONFIG_PATH"
ssh_exec "$CHECK_HELM_RELEASE_STATUS"

# Get all resources in the kube-system namespace to verify the installation
echo "Step 6 - Get all resources in the kube-system namespace"
GET_RESOURCES="kubectl --kubeconfig=${KUBECONFIG_PATH} get all -n kube-system"
ssh_exec "$GET_RESOURCES"
