#!/bin/bash
# Optimized Script: 4.2-build-deploy-remote-k3s.sh

set -x

# Remote server details
REMOTE_USER="super"
REMOTE_HOST="192.168.2.173"  # Adjust as needed
REMOTE_PORT="2222"

# Local directories and files
LOCAL_APP_DIR="/Users/pedroazevedo/Documents/Projects/CMS/CMS_App"
LOCAL_K3S_DIR="$LOCAL_APP_DIR/k3s"
LOCAL_REMOTE_DOCKERFILE="$LOCAL_APP_DIR/RemoteDockerfile"

# Remote directories
REMOTE_BASE_DIR="/home/super/Documents/Software/CMS"
REMOTE_APP_DIR="$REMOTE_BASE_DIR/app"
REMOTE_YAML_DIR="$REMOTE_BASE_DIR/yaml"
REMOTE_K3S_DIR="/etc/rancher/k3s"

# Image details
IMAGE_NAME="myapp"
IMAGE_TAG=$(date +%Y%m%d%H%M%S)  # Dynamic tag with current timestamp
REMOTE_REGISTRY="myregistry.local:5000"
FULL_IMAGE_NAME="$REMOTE_REGISTRY/$IMAGE_NAME:$IMAGE_TAG"

# App namespace
NAMESPACE="default"

# Build script based on the target environment
BUILD_SCRIPT="$LOCAL_APP_DIR/scripts/1.1-build-app-k3s-without-tests.sh"

# Step 0: Build Application
echo "Step 0: Building the application for k3s environment..."
$BUILD_SCRIPT

# Step 1: Determine latest JAR file
echo "Step 1: Determining the latest LOCAL JAR file..."
LATEST_JAR_FILE=$(ls -t $LOCAL_APP_DIR/target/cms_app-*.jar | head -1)
echo "Latest LOCAL JAR file: $LATEST_JAR_FILE"

# Step 2: Transfer Dockerfile and JAR to Remote Server
echo "Step 2: Transferring Dockerfile and JAR to the remote server..."
scp -P $REMOTE_PORT "$LOCAL_REMOTE_DOCKERFILE" "$LATEST_JAR_FILE" "$REMOTE_USER@$REMOTE_HOST:$REMOTE_APP_DIR"

# Step 3: Build and Push Docker Image on Remote Server
echo "Step 3: Building and pushing Docker image on the remote server..."
ssh -p $REMOTE_PORT $REMOTE_USER@$REMOTE_HOST <<EOF
cd $REMOTE_APP_DIR && \
docker build -t $FULL_IMAGE_NAME -f RemoteDockerfile . && \
docker push $FULL_IMAGE_NAME
EOF

# Step 4: Transfer and Apply Kubernetes Configurations
echo "Step 4: Deploying Kubernetes configurations..."
scp -P $REMOTE_PORT $LOCAL_K3S_DIR/deployment.yaml $LOCAL_K3S_DIR/ingress.yaml $LOCAL_K3S_DIR/k3s.yaml $LOCAL_K3S_DIR/postgres-deployment.yaml $LOCAL_K3S_DIR/postgres-pvc.yaml $LOCAL_K3S_DIR/postgres-service.yaml $LOCAL_K3S_DIR/prometheus-ingress.yaml $REMOTE_USER@$REMOTE_HOST:$REMOTE_YAML_DIR

# Step 4b: Update deployment.yaml with Latest Image
echo "Step 4b: Updating deployment.yaml to use the latest image"
sed -i '' "s|image: .*|image: $FULL_IMAGE_NAME|g" "$LOCAL_K3S_DIR/deployment.yaml"
scp -P $REMOTE_PORT "$LOCAL_K3S_DIR/deployment.yaml" "$REMOTE_USER@$REMOTE_HOST:$REMOTE_YAML_DIR/deployment.yaml"

# Step 4c: Transfer registries.yaml to the remote k3s directory with sudo privileges
echo "Step 4c: Transferring registries.yaml to the remote k3s directory with sudo privileges..."
cat "$LOCAL_K3S_DIR/registries.yaml" | ssh -p $REMOTE_PORT $REMOTE_USER@$REMOTE_HOST "sudo tee $REMOTE_K3S_DIR/registries.yaml > /dev/null"

# Step 5: Apply Kubernetes Configurations
echo "Step 5: Applying Kubernetes configurations..."
ssh -p $REMOTE_PORT $REMOTE_USER@$REMOTE_HOST "KUBECONFIG=$REMOTE_YAML_DIR/k3s.yaml kubectl apply -f $REMOTE_YAML_DIR"

# Step 6: Restart Deployment
echo "Step 6: Restarting deployment to use the new image..."
ssh -p $REMOTE_PORT $REMOTE_USER@$REMOTE_HOST "KUBECONFIG=$REMOTE_YAML_DIR/k3s.yaml kubectl rollout restart deployment -n $NAMESPACE -l app=myapp"

echo "Deployment process completed."
