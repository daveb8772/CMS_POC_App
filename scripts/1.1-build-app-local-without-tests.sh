#!/bin/zsh
# Build script for local environment without tests

# Define Maven project root directory
PROJECT_ROOT_DIR="../"  # Adjust this to your project directory

# Invoke the common build function with parameters
source 1.1-build-app.sh "no" "local"
