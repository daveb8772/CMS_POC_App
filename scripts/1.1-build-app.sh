#!/bin/zsh

# Define the root directory of the Maven project
PROJECT_ROOT_DIR="../" # Adjust this path to the root directory of your Maven project

# Function to build the app using Maven
build_app() {
    local build_with_tests=$1

    echo "Building the application using Maven..."
    if [ "$build_with_tests" = "yes" ]; then
        (cd "$PROJECT_ROOT_DIR" && mvn clean package -Pwith-tests)
    else
        (cd "$PROJECT_ROOT_DIR" && mvn clean package -Pwithout-tests)
    fi

    if [[ $? -ne 0 ]]; then
        echo "Failed to build the application."
        exit 1
    fi
}


# Check if a parameter is provided
if [ -z "$1" ]; then
    echo "Using default - Build without tests"
    echo "To change behaviour call script with:"
    echo "yes - Build with tests"
    echo "no - Build without tests"
    build_app "no"  # Set default value to "no"
else
    case "$1" in
        "yes"|"no")
            build_app "$1"
            ;;
        *)
            echo "Invalid argument. Usage: $0 [yes|no]"
            echo "yes - Build with tests"
            echo "no - Build without tests"
            exit 1
            ;;
    esac
fi
