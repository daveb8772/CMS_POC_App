#!/bin/zsh

# Define the path to the Dockerfile relative to the script location
DOCKERFILE_PATH="../Dockerfile"

# Check if Dockerfile exists at the specified path
if [[ -f "$DOCKERFILE_PATH" ]]; then
    echo "Dockerfile found. Proceeding with the build process."

    # Navigate to the directory where the Dockerfile is located
    cd "$(dirname "$DOCKERFILE_PATH")" || exit

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
