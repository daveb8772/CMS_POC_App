#!/bin/zsh

# Define your AKS context
aks_context="my-aks-cluster"

# Get the current context
current_context=$(kubectl config current-context)

# Check if the current context is AKS, if not switch to it
if [ "$current_context" != "$aks_context" ]; then
    echo "Switching to AKS context..."
    az aks get-credentials --name my-aks-cluster --resource-group k8s-resource-group
    kubectl config use-context "$aks_context"
else
    echo "Already in AKS context."
fi

#!/bin/zsh

# Define paths to your YAML files
DEPLOYMENT_YAML_PATH="../k8s/deployment.yaml"
SERVICE_YAML_PATH="../k8s/service.yaml"
POSTGRES_DEPLOYMENT_YAML_PATH="../k8s/postgres-deployment.yaml"
POSTGRES_SERVICE_YAML_PATH="../k8s/postgres-service.yaml"
POSTGRES_PVC_YAML_PATH="../k8s/postgres-pvc.yaml"
PROMETHEUS_YAML_PATH="../k8s/prometheus.yaml"
GRAFANA_YAML_PATH="../k8s/grafana.yaml"

# Deploy PostgreSQL
echo "Deploying PostgreSQL..."
kubectl apply -f "$POSTGRES_PVC_YAML_PATH"
kubectl apply -f "$POSTGRES_DEPLOYMENT_YAML_PATH"
kubectl apply -f "$POSTGRES_SERVICE_YAML_PATH"

# Deploy Application
echo "Deploying Application..."
kubectl apply -f "$DEPLOYMENT_YAML_PATH"
kubectl apply -f "$SERVICE_YAML_PATH"

# Deploy Prometheus
echo "Deploying Prometheus..."
kubectl apply -f "$PROMETHEUS_YAML_PATH"

# Deploy Grafana
echo "Deploying Grafana..."
kubectl apply -f "$GRAFANA_YAML_PATH"

# Force redeployment
echo "Forcing redeployment..."
kubectl patch deployment myapp-deployment -p \
  "{\"spec\":{\"template\":{\"metadata\":{\"annotations\":{\"redeployed-at\":\"$(date +'%s')\"}}}}}"

# List resources
echo "Listing deployed resources..."
kubectl get nodes
kubectl get pods
kubectl get svc

echo "Deployment to AKS completed."
