#!/bin/zsh
# file: 4.4-deploy-metrics-server.sh

set -x

# Local Helm directory path
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

# Step 1: Check if Helm is installed and install if not
echo "Step 1 - Checking if Helm is installed on the remote server..."
CHECK_HELM_INSTALLED='helm version --short > /dev/null 2>&1 || (echo "Helm not found, installing..." && curl https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3 | bash)'
ssh_exec "$CHECK_HELM_INSTALLED"

# Step 2: Add the Metrics Server Helm chart repository and update
echo "Step 2 - Adding Metrics Server Helm chart repository on the remote server..."
ADD_HELM_REPO="helm repo add metrics-server https://kubernetes-sigs.github.io/metrics-server/ && helm repo update"
ssh_exec "$ADD_HELM_REPO"

# Step 3: Fetch and untar the Metrics Server chart to local helm directory
echo "Step 3a - Fetching the latest Metrics Server Helm chart to local directory..."
helm fetch metrics-server/metrics-server --untar --untardir $LOCAL_INSTALL_HELM_PATH

# Step 3b: Copy Metrics Server setup files to the remote server
echo "Step 3b - Copying Metrics Server setup files to the remote server..."
scp -P "$REMOTE_PORT" -r $LOCAL_HELM_PATH/* "$REMOTE_USER@$REMOTE_SERVER:$REMOTE_APP_COPY_PATH"

# Step 4a: Delete existing metrics-server resources not managed by Helm
echo "Step 4a - Deleting existing metrics-server resources not managed by Helm..."
DELETE_EXISTING_RESOURCES="kubectl --kubeconfig=${KUBECONFIG_PATH} delete svc,deploy,sa --namespace kube-system --selector=app.kubernetes.io/name=metrics-server"
ssh_exec "$DELETE_EXISTING_RESOURCES"

# Step 4b: Delete existing metrics-server ClusterRole and ClusterRoleBinding not managed by Helm
echo "Step 4b - Deleting existing metrics-server ClusterRole and ClusterRoleBinding not managed by Helm..."
DELETE_EXISTING_CR="kubectl --kubeconfig=${KUBECONFIG_PATH} delete clusterrole system:metrics-server"
ssh_exec "$DELETE_EXISTING_CR"
DELETE_EXISTING_CRB="kubectl --kubeconfig=${KUBECONFIG_PATH} delete clusterrolebinding system:metrics-server"
ssh_exec "$DELETE_EXISTING_CRB"
DELETE_EXISTING_CRB_AUTH_DELEGATOR="kubectl --kubeconfig=${KUBECONFIG_PATH} delete clusterrolebinding metrics-server:system:auth-delegator"
ssh_exec "$DELETE_EXISTING_CRB_AUTH_DELEGATOR"
DELETE_EXISTING_RB="kubectl --kubeconfig=${KUBECONFIG_PATH} delete rolebinding metrics-server-auth-reader --namespace kube-system"
ssh_exec "$DELETE_EXISTING_RB"
DELETE_EXISTING_SVC="kubectl --kubeconfig=${KUBECONFIG_PATH} delete svc metrics-server --namespace kube-system"
ssh_exec "$DELETE_EXISTING_SVC"
DELETE_EXISTING_DEPLOY="kubectl --kubeconfig=${KUBECONFIG_PATH} delete deployment metrics-server --namespace kube-system"
ssh_exec "$DELETE_EXISTING_DEPLOY"


# Step 4c: Apply Helm chart for Metrics Server
echo "Step 4c - Applying Helm chart for Metrics Server on the remote server..."
APPLY_METRICS_SERVER_CHART="helm upgrade --install metrics-server metrics-server/metrics-server --namespace kube-system --create-namespace --kubeconfig $KUBECONFIG_PATH -f $REMOTE_CHART_PATH/my-values.yaml"
ssh_exec "$APPLY_METRICS_SERVER_CHART"

# Step 5: Check the status of the Helm release
echo "Step 5 - Checking the status of the Helm release on the remote server..."
CHECK_HELM_RELEASE_STATUS="helm status metrics-server --namespace kube-system --kubeconfig $KUBECONFIG_PATH"
ssh_exec "$CHECK_HELM_RELEASE_STATUS"

# Step 6: Get all resources in the kube-system namespace to verify the installation
echo "Step 6 - Getting all resources in the kube-system namespace on the remote server..."
GET_RESOURCES="kubectl --kubeconfig=${KUBECONFIG_PATH} get all -n kube-system"
ssh_exec "$GET_RESOURCES"
