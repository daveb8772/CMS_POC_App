#!/bin/zsh
# Build script for Kubernetes environment with tests

# Define Maven project root directory
PROJECT_ROOT_DIR="../"  # Adjust this to your project directory

# Invoke the common build function with parameters
source 1.1-build-app.sh "yes" "k8s"
