#!/bin/zsh

# Define the path to the Dockerfile relative to the script location
DOCKERFILE_PATH="../Dockerfile"

# Function to check Azure login
check_azure_login() {
    # Check if already logged in to Azure
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

# Function to check ACR login
# Function to check ACR login
check_acr_login() {
    echo "Logging in to Azure Container Registry..."
    az acr login --name myacr351f520047da48a7
    if [[ $? -ne 0 ]]; then
        echo "Failed to log in to Azure Container Registry."
        exit 1
    fi
}


# Check if Dockerfile exists at the specified path
if [[ -f "$DOCKERFILE_PATH" ]]; then
    echo "Dockerfile found. Proceeding with the build process."

    # Navigate to the directory where the Dockerfile is located
    cd "$(dirname "$DOCKERFILE_PATH")" || exit

    echo "Check Azure and ACR login"
    # Check Azure and ACR login
    check_azure_login
    check_acr_login

    # Build and push the Docker image
    docker build -t myacr351f520047da48a7.azurecr.io/myapp:v1 .
    docker push myacr351f520047da48a7.azurecr.io/myapp:v1

    # Check if the image was built and pushed successfully
    if [[ $? -eq 0 ]]; then
        echo "Docker image built and pushed successfully."
    else
        echo "Failed to build and/or push Docker image."
    fi
else
    echo "Dockerfile not found at $DOCKERFILE_PATH. Please ensure you are in the right directory."
    exit 1
fi
