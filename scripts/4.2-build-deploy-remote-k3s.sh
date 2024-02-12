#!/bin/bash

# Define remote server details
REMOTE_USER="super"
REMOTE_HOST="super"  # Update this with the remote server's hostname or IP address
REMOTE_PORT="2222"

# Define local directories and files
LOCAL_APP_DIR="/Users/pedroazevedo/Documents/Projects/CMS/CMS_App"
LOCAL_JAR_FILE="$LOCAL_APP_DIR/target/cms_app-1.0.0.jar"
LOCAL_K8S_DIR="$LOCAL_APP_DIR/k8s"
LOCAL_REMOTE_DOCKERFILE="$LOCAL_APP_DIR/RemoteDockerfile"

# Define remote directories
REMOTE_BASE_DIR="/home/super/Documents/Software/CMS"
REMOTE_APP_DIR="$REMOTE_BASE_DIR/app"
REMOTE_YAML_DIR="$REMOTE_BASE_DIR/yaml"

# Image details
IMAGE_NAME="myapp"
IMAGE_TAG="latest"
REMOTE_REGISTRY="localhost:5000"
FULL_IMAGE_NAME="$REMOTE_REGISTRY/$IMAGE_NAME:$IMAGE_TAG"

# Step 1: Transfer the RemoteDockerfile and JAR file to the remote server
echo "Transferring Dockerfile and JAR to the remote server..."
scp -P $REMOTE_PORT "$LOCAL_REMOTE_DOCKERFILE" "$REMOTE_USER@$REMOTE_HOST:$REMOTE_APP_DIR/Dockerfile"
scp -P $REMOTE_PORT "$LOCAL_JAR_FILE" "$REMOTE_USER@$REMOTE_HOST:$REMOTE_APP_DIR"

# Step 2: Build and push the Docker image on the remote server
echo "Building and pushing Docker image on the remote server..."
ssh -p $REMOTE_PORT $REMOTE_USER@$REMOTE_HOST "cd $REMOTE_APP_DIR && docker build -t $FULL_IMAGE_NAME -f Dockerfile . && docker push $FULL_IMAGE_NAME"

# Step 3: Transfer and apply Kubernetes configurations
echo "Deploying Kubernetes configurations..."
scp -P $REMOTE_PORT $LOCAL_K8S_DIR/*.yaml $REMOTE_USER@$REMOTE_HOST:$REMOTE_YAML_DIR

echo "Applying Kubernetes configurations..."
for yaml_file in $(ls $LOCAL_K8S_DIR/*.yaml | xargs -n 1 basename); do
    ssh -p $REMOTE_PORT $REMOTE_USER@$REMOTE_HOST "KUBECONFIG=$REMOTE_YAML_DIR/k3s.yaml kubectl apply -f $REMOTE_YAML_DIR/$yaml_file"
done

echo "Deployment process completed."
