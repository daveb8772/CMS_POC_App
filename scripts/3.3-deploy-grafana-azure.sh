#!/bin/zsh

# Define your AKS cluster name and resource group
AKS_CLUSTER_NAME="my-aks-cluster"
AKS_RESOURCE_GROUP="k8s-resource-group"

# Set the namespace where Grafana will be installed
NAMESPACE="grafana"

# Get AKS credentials to set the correct context
echo "Setting kubectl context to AKS cluster '$AKS_CLUSTER_NAME'..."
az aks get-credentials --name $AKS_CLUSTER_NAME --resource-group $AKS_RESOURCE_GROUP --overwrite-existing

# Check if we are now in the correct context
CURRENT_CONTEXT=$(kubectl config current-context)
EXPECTED_CONTEXT="aks_${AKS_RESOURCE_GROUP}_${AKS_CLUSTER_NAME}"

if [ "$CURRENT_CONTEXT" = "$EXPECTED_CONTEXT" ]; then
    echo "Current context is '$CURRENT_CONTEXT'. Proceeding with deployment..."
else
    echo "Error: Current context is '$CURRENT_CONTEXT' but expected context is '$EXPECTED_CONTEXT'. Aborting deployment."
    exit 1
fi

# Deploy Grafana using Helm
echo "Deploying Grafana to the AKS cluster '$AKS_CLUSTER_NAME'..."
helm repo add grafana https://grafana.github.io/helm-charts
helm repo update

# Prompt for Grafana admin password to avoid hardcoding
read "grafanaPassword?Enter Grafana admin password: "

# Install or upgrade Grafana
helm upgrade --install grafana grafana/grafana \
  --namespace $NAMESPACE \
  --create-namespace \
  --set persistence.enabled=true \
  --set adminPassword="$grafanaPassword" \
  --set service.type=LoadBalancer

echo "Grafana has been deployed in the $NAMESPACE namespace."
