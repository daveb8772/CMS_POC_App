#!/bin/zsh

# Log in to Azure (this will open a browser window for authentication)
echo "Logging in to Azure..."
az login

# Getting credentials for your AKS cluster (replace <Your-AKS-Cluster-Name> and <Your-Resource-Group-Name> with your actual AKS cluster name and resource group name)
echo "Setting kubectl context to your AKS cluster..."
az aks get-credentials --name my-aks-cluster --resource-group k8s-resource-group

# Now, similar to your local checks, list the Kubernetes resources
echo "Listing Kubernetes nodes, pods, and services in AKS..."
kubectl config get-contexts
kubectl get nodes
kubectl get pods
kubectl get pods -n kube-system
kubectl get svc
