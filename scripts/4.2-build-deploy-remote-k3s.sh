#!/bin/bash
set -x

# Define remote server details
REMOTE_USER="super"
#REMOTE_HOST="172.16.79.128"  # Use your server's hostname or IP address
REMOTE_HOST="192.168.2.173"  # Use your server's hostname or IP address
REMOTE_PORT="2222"

# Define local directories and files
LOCAL_APP_DIR="/Users/pedroazevedo/Documents/Projects/CMS/CMS_App"
LOCAL_K3S_DIR="$LOCAL_APP_DIR/k3s"
LOCAL_REMOTE_DOCKERFILE="$LOCAL_APP_DIR/RemoteDockerfile"

# Define remote directories
REMOTE_BASE_DIR="/home/super/Documents/Software/CMS"
REMOTE_APP_DIR="$REMOTE_BASE_DIR/app"
REMOTE_YAML_DIR="$REMOTE_BASE_DIR/yaml"

# Image details with dynamic tagging
IMAGE_NAME="myapp"
IMAGE_TAG=$(date +%Y%m%d%H%M%S)  # Using current timestamp as a dynamic tag
REMOTE_REGISTRY="myregistry.local:5000"
FULL_IMAGE_NAME="$REMOTE_REGISTRY/$IMAGE_NAME:$IMAGE_TAG"

# Choose the build script based on the target environment
BUILD_SCRIPT="$LOCAL_APP_DIR/scripts/1.1-build-app-k3s-without-tests.sh"

# Step 0: Build the application with the appropriate script for k3s environment
echo "Step 0: Building the application for k3s environment..."
$BUILD_SCRIPT

# Determine the latest JAR file name
echo "Step 1: Determine the latest LOCAL JAR file name"
LATEST_JAR_FILE=$(ls -t $LOCAL_APP_DIR/target/cms_app-*.jar | head -1)
echo "Latest LOCAL JAR file: $LATEST_JAR_FILE"

# Step 1: Transfer the RemoteDockerfile and the latest JAR file to the remote server
echo "Step 1: Transferring Dockerfile and the latest JAR to the remote server..."
scp -P $REMOTE_PORT "$LOCAL_REMOTE_DOCKERFILE" "$REMOTE_USER@$REMOTE_HOST:$REMOTE_APP_DIR/Dockerfile"
scp -P $REMOTE_PORT "$LATEST_JAR_FILE" "$REMOTE_USER@$REMOTE_HOST:$REMOTE_APP_DIR/app.jar"

# Step 2: Build and push the Docker image on the remote server
echo "Step 2: Building and pushing Docker image on the remote server..."
ssh -p $REMOTE_PORT $REMOTE_USER@$REMOTE_HOST "cd $REMOTE_APP_DIR && docker build -t $FULL_IMAGE_NAME -f Dockerfile . && docker push $FULL_IMAGE_NAME"

# Step 3: Transfer and apply Kubernetes configurations
echo "Step 3: Deploying Kubernetes configurations..."
# Note: Assumes all .yaml files need to be applied. Adjust if necessary.
scp -P $REMOTE_PORT $LOCAL_K3S_DIR/*.yaml $REMOTE_USER@$REMOTE_HOST:$REMOTE_YAML_DIR

# Manually updating the image in deployment.yaml to ensure the use of the latest image
echo "Step 3b: Manually updating the image in deployment.yaml to ensure the use of the latest image"
sed -i '' "s|image: .*|image: $FULL_IMAGE_NAME|g" "$LOCAL_K3S_DIR/deployment.yaml"
scp -P $REMOTE_PORT "$LOCAL_K3S_DIR/deployment.yaml" "$REMOTE_USER@$REMOTE_HOST:$REMOTE_YAML_DIR/deployment.yaml"

echo "Step 4: Applying Kubernetes configurations..."
for yaml_file in $(ls $LOCAL_K3S_DIR/*.yaml | xargs -n 1 basename); do
    ssh -p $REMOTE_PORT $REMOTE_USER@$REMOTE_HOST "KUBECONFIG=$REMOTE_YAML_DIR/k3s.yaml kubectl apply -f $REMOTE_YAML_DIR/$yaml_file"
done
# Automatically find the deployment name and restart it
DEPLOYMENT_NAME=$(ssh -p $REMOTE_PORT $REMOTE_USER@$REMOTE_HOST "grep -oP 'kind: Deployment\nmetadata:\n\s+name: \K(\S+)' $REMOTE_YAML_DIR/*.yaml | head -1")
NAMESPACE="default"  # Adjust this as needed

echo "Restarting deployment: $DEPLOYMENT_NAME"
ssh -p $REMOTE_PORT $REMOTE_USER@$REMOTE_HOST "KUBECONFIG=$REMOTE_YAML_DIR/k3s.yaml kubectl rollout restart deployment $DEPLOYMENT_NAME -n $NAMESPACE"

NAMESPACE="default"  # Adjust this as needed

# Automatically fetch the specific deployment using the new image and restart it
echo "Final Step: Fetching and restarting the deployment..."
DEPLOYMENT_NAME=$(ssh -p $REMOTE_PORT $REMOTE_USER@$REMOTE_HOST "KUBECONFIG=$REMOTE_YAML_DIR/k3s.yaml kubectl get deployments -n $NAMESPACE -o=jsonpath='{.items[?(@.spec.template.spec.containers[*].image==\"$FULL_IMAGE_NAME\")].metadata.name}'")

if [ -n "$DEPLOYMENT_NAME" ]; then
    echo "Restarting deployment: $DEPLOYMENT_NAME"
    ssh -p $REMOTE_PORT $REMOTE_USER@$REMOTE_HOST "KUBECONFIG=$REMOTE_YAML_DIR/k3s.yaml kubectl rollout restart deployment $DEPLOYMENT_NAME -n $NAMESPACE"
else
    echo "Deployment name not found for image $FULL_IMAGE_NAME, skipping restart."
fi

echo "Deployment process completed."
