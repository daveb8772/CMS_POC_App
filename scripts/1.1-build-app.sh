#!/bin/zsh

# Define the root directory of the Maven project
PROJECT_ROOT_DIR="../"  # Adjust this path
PROPERTIES_FILE="$PROJECT_ROOT_DIR/src/main/resources/application.properties"

# Function to read spring.profiles.active
get_spring_profile() {
    grep "^spring.profiles.active=" "$PROPERTIES_FILE" | cut -d'=' -f2
}

# Function to increment version
increment_version() {
    pushd "$PROJECT_ROOT_DIR" > /dev/null  # Go to the root directory of the project
    # Generate a version number based on date and time (e.g., 1.0.20230215.1234)
    local new_version="1.0.$(date +%Y%m%d).$(date +%H%M)"
    echo "New version: $new_version"
    # Update the version in pom.xml
    mvn versions:set -DnewVersion=$new_version
    popd > /dev/null  # Return to the original directory
    echo $new_version
}

# Function to build the app using Maven
build_app() {
    local build_with_tests=$1
    local env=$2  # 'local' or 'k8s'
    local spring_profile=$(get_spring_profile)
    local app_version=$(increment_version)

    echo "Building the application using Maven with Spring profile: $spring_profile, Environment: $env, Version: $app_version"
    if [ "$build_with_tests" = "yes" ]; then
        (cd "$PROJECT_ROOT_DIR" && mvn clean package -Pwith-tests,$env -Dspring.profiles.active=$spring_profile)
    else
        (cd "$PROJECT_ROOT_DIR" && mvn clean package -Pwithout-tests,$env -Dspring.profiles.active=$spring_profile)
    fi

    if [[ $? -ne 0 ]]; then
        echo "Failed to build the application."
        exit 1
    fi
}

# Check if arguments are provided for test option and environment
if [ -z "$1" ] || [ -z "$2" ]; then
    echo "Usage: $0 [yes|no] [local|k8s]"
    echo "yes - Build with tests"
    echo "no - Build without tests"
    echo "local - Use local database configuration"
    echo "k8s - Use Kubernetes database configuration"
    exit 1
else
    build_app "$1" "$2"
fi
