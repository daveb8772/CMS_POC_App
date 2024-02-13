#!/bin/zsh

# Define your AKS cluster name and resource group
AKS_CLUSTER_NAME="my-aks-cluster"
AKS_RESOURCE_GROUP="k8s-resource-group"

# Set the namespace where Grafana will be installed
NAMESPACE="grafana"

# Confirm with the user before proceeding
echo "This script will deploy Grafana to the AKS cluster '$AKS_CLUSTER_NAME' in namespace '$NAMESPACE'."
read "proceed?Are you sure? (y/n): "
if [[ ! $proceed =~ ^[Yy]$ ]]
then
    echo "Deployment cancelled."
    exit 1
fi

# Get AKS credentials to set the correct context
echo "Setting kubectl context to AKS cluster '$AKS_CLUSTER_NAME'..."
az aks get-credentials --name $AKS_CLUSTER_NAME --resource-group $AKS_RESOURCE_GROUP --overwrite-existing

# Dynamically validate the current context matches the intended AKS cluster
CURRENT_CONTEXT=$(kubectl config current-context)
EXPECTED_CONTEXT=$(az aks show --name $AKS_CLUSTER_NAME --resource-group $AKS_RESOURCE_GROUP --query 'id' -o tsv | awk -F'/' '{print $NF}')
echo "Current context is : '$CURRENT_CONTEXT'"
echo "Expected context is : '$EXPECTED_CONTEXT'"


if [[ "$CURRENT_CONTEXT" == "$EXPECTED_CONTEXT" ]] || [[ "$CURRENT_CONTEXT" == "$AKS_CLUSTER_NAME" ]]; then
    echo "Current context is correct: '$CURRENT_CONTEXT'. Proceeding with deployment..."
else
    echo "Error: Mismatch in current context. Found: '$CURRENT_CONTEXT', Expected: '$EXPECTED_CONTEXT' or '$AKS_CLUSTER_NAME'. Aborting deployment."
    exit 1
fi


# Deploy Grafana using Helm
echo "Deploying Grafana to the AKS cluster '$AKS_CLUSTER_NAME'..."
helm repo add grafana https://grafana.github.io/helm-charts
helm repo update

# Prompt for Grafana admin password
echo "Please enter Grafana admin password:"
read -s grafanaPassword

# Install or upgrade Grafana
helm upgrade --install grafana grafana/grafana \
  --namespace $NAMESPACE \
  --create-namespace \
  --set persistence.enabled=true \
  --set adminPassword="$grafanaPassword" \
  --set service.type=LoadBalancer

echo "Grafana has been deployed in the $NAMESPACE namespace."
