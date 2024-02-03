#!/bin/zsh

# Define AKS and ACR details
AKS_CLUSTER_NAME="my-aks-cluster"
AKS_RESOURCE_GROUP="k8s-resource-group"
ACR_NAME="myacr351f520047da48a7"
DOCKERFILE_PATH="../Dockerfile"

# Function to check Azure login
check_azure_login() {
    echo "Checking Azure login..."
    az account show > /dev/null 2>&1
    if [[ $? -ne 0 ]]; then
        echo "Logging in to Azure..."
        az login
        if [[ $? -ne 0 ]]; then
            echo "Failed to log in to Azure."
            exit 1
        fi
    else
        echo "Already logged in to Azure."
    fi
}

# Function to login to Azure Container Registry
check_acr_login() {
    echo "Logging in to Azure Container Registry..."
    az acr login --name $ACR_NAME
    if [[ $? -ne 0 ]]; then
        echo "Failed to log in to Azure Container Registry."
        exit 1
    else
        echo "Successfully logged in to ACR."
    fi
}

# Function to check and attach ACR to AKS if not already attached
attach_acr_to_aks() {
    echo "Attaching ACR to AKS..."
    # Check if the AKS cluster is already attached to the ACR
    # This command tries to get the ACR ID that the AKS is attached to. If not attached, it will be empty.
    ACR_ATTACHED=$(az aks show --name $AKS_CLUSTER_NAME --resource-group $AKS_RESOURCE_GROUP --query "identityProfile.kubeletidentity.clientId" -o tsv)

    # If ACR_ATTACHED is empty, attach the ACR to AKS
    if [[ -z "$ACR_ATTACHED" ]]; then
        echo "AKS cluster is not attached to ACR. Attaching now..."
        az aks update --name $AKS_CLUSTER_NAME --resource-group $AKS_RESOURCE_GROUP --attach-acr $ACR_NAME
        if [[ $? -ne 0 ]]; then
            echo "Failed to attach ACR to AKS."
            exit 1
        else
            echo "Successfully attached ACR to AKS."
        fi
    else
        echo "AKS cluster is already attached to ACR."
    fi
}

# Build and push Docker image
build_and_push_image() {
    if [[ -f "$DOCKERFILE_PATH" ]]; then
        echo "Dockerfile found. Proceeding with the build process."
        cd "$(dirname "$DOCKERFILE_PATH")" || exit
        docker build -t $ACR_NAME.azurecr.io/myapp:v1 .
        docker push $ACR_NAME.azurecr.io/myapp:v1
        if [[ $? -eq 0 ]]; then
            echo "Docker image built and pushed successfully."
        else
            echo "Failed to build and/or push Docker image."
            exit 1
        fi
    else
        echo "Dockerfile not found at $DOCKERFILE_PATH. Please ensure you are in the right directory."
        exit 1
    fi
}

# Main script starts here
check_azure_login
check_acr_login
attach_acr_to_aks
build_and_push_image
