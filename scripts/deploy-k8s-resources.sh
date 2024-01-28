#!/bin/zsh

# Define paths to your YAML files relative to the script location
DEPLOYMENT_YAML_PATH="../k8s/deployment.yaml"
SERVICE_YAML_PATH="../k8s/service.yaml"
POSTGRES_DEPLOYMENT_YAML_PATH="../k8s/postgres-deployment.yaml"
POSTGRES_SERVICE_YAML_PATH="../k8s/postgres-service.yaml"
POSTGRES_PVC_YAML_PATH="../k8s/postgres-pvc.yaml"

echo "Deploying PostgreSQL PersistentVolumeClaim..."
kubectl apply -f "$POSTGRES_PVC_YAML_PATH"

echo "Deploying PostgreSQL resources..."
kubectl apply -f "$POSTGRES_DEPLOYMENT_YAML_PATH"
kubectl apply -f "$POSTGRES_SERVICE_YAML_PATH"

echo "Deploying Application resources..."
kubectl apply -f "$DEPLOYMENT_YAML_PATH"
kubectl apply -f "$SERVICE_YAML_PATH"

echo "Listing Kubernetes nodes, pods, and services..."
kubectl get nodes
kubectl get pods
kubectl get svc

echo "Kubernetes deployment script execution completed."

