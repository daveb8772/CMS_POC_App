#!/bin/zsh

# Local setup for building the image
PROJECT_DIR="/Users/pedroazevedo/Documents/Projects/CMS/CMS_App" # Local project directory
IMAGE_NAME="myapp" # Name of your app
IMAGE_VERSION="v1" # Version tag for your image
DOCKER_FILE_PATH="$PROJECT_DIR/Dockerfile" # Path to your Dockerfile

# Remote Docker registry setup
REMOTE_USER="super"
REMOTE_SERVER="super" # IP or hostname of your remote server
REMOTE_REGISTRY_PORT="5000"
REMOTE_IMAGE="$REMOTE_USER@$REMOTE_SERVER:$REMOTE_REGISTRY_PORT/$IMAGE_NAME:$IMAGE_VERSION"

# Remote Kubernetes deployment setup
REMOTE_YAML_DIR="/home/super/Documents/Software/CMS/yaml" # Remote directory for YAML files

# Step 1: Build the Docker image locally
echo "Building Docker image locally..."
docker build -t $IMAGE_NAME:$IMAGE_VERSION -f $DOCKER_FILE_PATH $PROJECT_DIR

# Step 2: Tag the image for the remote registry
echo "Tagging the image for the remote registry..."
docker tag $IMAGE_NAME:$IMAGE_VERSION $REMOTE_IMAGE

# Ensure Docker is logged in to remote server if necessary

# Step 3: Push the image to the remote Docker registry
echo "Pushing the image to the remote Docker registry..."
docker push $REMOTE_IMAGE

# Step 4: Copy YAML files to the remote server
echo "Copying YAML files to the remote server..."
scp $PROJECT_DIR/k8s/*.yaml $REMOTE_USER@$REMOTE_SERVER:$REMOTE_YAML_DIR/

# Step 5: SSH into the remote server and apply YAML configurations
echo "Applying Kubernetes configurations on the remote server..."
ssh $REMOTE_USER@$REMOTE_SERVER << EOF
kubectl apply -f $REMOTE_YAML_DIR/deployment.yaml
kubectl apply -f $REMOTE_YAML_DIR/postgres-deployment.yaml
kubectl apply -f $REMOTE_YAML_DIR/postgres-pvc.yaml
kubectl apply -f $REMOTE_YAML_DIR/postgres-service.yaml
kubectl apply -f $REMOTE_YAML_DIR/service.yaml
EOF

echo "Deployment process completed."
