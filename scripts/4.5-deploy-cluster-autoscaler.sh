#!/bin/zsh
# file: deploy-cluster-autoscaler.sh
# Only for cloud setups with multiple nodes
# See https://github.com/kubernetes/autoscaler/blob/master/cluster-autoscaler

set -x

# Local Helm directory path for Cluster Autoscaler
LOCAL_INSTALL_HELM_PATH="../k3s"
LOCAL_HELM_PATH="../k3s/cluster-autoscaler"
LOCAL_HELM_SETUP_PATH="../k3s/cluster-autoscaler-setup"

# Variables for remote server connection
REMOTE_USER="super"
REMOTE_SERVER="192.168.2.173"
REMOTE_PORT="2222"
KUBECONFIG_PATH="/home/super/Documents/Software/CMS/yaml/k3s.yaml"
REMOTE_APP_COPY_PATH="/home/super/Documents/Software/CMS"
REMOTE_CHART_PATH="/home/super/Documents/Software/CMS/cluster-autoscaler"
REMOTE_CHART_CONFIG="$REMOTE_CHART_PATH/my-values.yaml"

# Function to execute SSH commands
ssh_exec() {
    ssh -p "$REMOTE_PORT" "$REMOTE_USER@$REMOTE_SERVER" "$1"
}
# Function to clean up existing Helm chart directory
cleanup_local_chart_dir() {
    echo "Cleaning up local chart directory: $LOCAL_INSTALL_HELM_PATH/cluster-autoscaler"
    rm -rf "$LOCAL_INSTALL_HELM_PATH/cluster-autoscaler"
}

# Step 1: Check if Helm is installed and install if not
echo "Step 1 - Checking if Helm is installed on the remote server..."
CHECK_HELM_INSTALLED='helm version --short > /dev/null 2>&1 || (echo "Helm not found, installing..." && curl https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3 | bash)'
ssh_exec "$CHECK_HELM_INSTALLED"

# Step 2: Add the Cluster Autoscaler Helm chart repository and update
echo "Step 2 - Adding Cluster Autoscaler Helm chart repository on the remote server..."
ADD_HELM_REPO="helm repo add autoscaler https://kubernetes.github.io/autoscaler/ && helm repo update"
ssh_exec "$ADD_HELM_REPO"

# Step 3: Fetch and untar the Cluster Autoscaler chart to local helm directory
echo "Step 3 - Fetching the latest Cluster Autoscaler Helm chart to local directory..."
cleanup_local_chart_dir
helm fetch autoscaler/cluster-autoscaler --untar --untardir $LOCAL_INSTALL_HELM_PATH

# Step 4: Copy Cluster Autoscaler Helm chart and values file to the remote server
echo "Step 4 - Copying Cluster Autoscaler Helm chart and values file to the remote server..."
scp -P "$REMOTE_PORT" -r $LOCAL_INSTALL_HELM_PATH/cluster-autoscaler "$REMOTE_USER@$REMOTE_SERVER:$REMOTE_APP_COPY_PATH"
scp -P "$REMOTE_PORT" $LOCAL_HELM_SETUP_PATH/my-values.yaml "$REMOTE_USER@$REMOTE_SERVER:$REMOTE_CHART_CONFIG"

# Step 5: Install or upgrade the Cluster Autoscaler using the Helm chart and custom values on the remote server
echo "Step 5 - Installing or upgrading Cluster Autoscaler on the remote server..."
APPLY_CA_CHART="helm upgrade --install cluster-autoscaler $REMOTE_CHART_PATH --namespace kube-system --create-namespace --kubeconfig $KUBECONFIG_PATH -f $REMOTE_CHART_CONFIG"
ssh_exec "$APPLY_CA_CHART"

# Step 6: Check the status of the Helm release
echo "Step 6 - Checking the status of the Helm release on the remote server..."
CHECK_HELM_RELEASE_STATUS="helm status cluster-autoscaler --namespace kube-system --kubeconfig $KUBECONFIG_PATH"
ssh_exec "$CHECK_HELM_RELEASE_STATUS"

# Step 7: Get all resources in the kube-system namespace to verify the installation
echo "Step 7 - Getting all resources in the kube-system namespace on the remote server..."
GET_RESOURCES="kubectl --kubeconfig=${KUBECONFIG_PATH} get all -n kube-system"
ssh_exec "$GET_RESOURCES"
