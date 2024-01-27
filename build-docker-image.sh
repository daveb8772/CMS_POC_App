#!/bin/zsh

# Check if Dockerfile exists in the current directory
if [[ -f "Dockerfile" ]]; then
    echo "Dockerfile found. Proceeding with the build process."

    # Your existing build commands here
    docker build -t myacr351f520047da48a7.azurecr.io/myapp:v1 .
    docker push myacr351f520047da48a7.azurecr.io/myapp:v1

    # Check if the image was built successfully
	if [[ $? -eq 0 ]]; then
    		echo "Docker image built successfully."
	else
    		echo "Failed to build Docker image."
	fi

else
    echo "Dockerfile not found in the current directory. Please ensure you are in the right directory."
    exit 1
fi
