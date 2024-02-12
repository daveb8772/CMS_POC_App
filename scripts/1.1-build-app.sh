#!/bin/zsh

# Define the root directory of the Maven project
PROJECT_ROOT_DIR="../" # Adjust this path to the root directory of your Maven project
PROPERTIES_FILE="$PROJECT_ROOT_DIR/src/main/resources/application.properties" # Adjust the path to your application.properties file

# Function to read spring.profiles.active from application.properties
get_spring_profile() {
    grep "^spring.profiles.active=" "$PROPERTIES_FILE" | cut -d'=' -f2
}

# Function to build the app using Maven
build_app() {
    local build_with_tests=$1
    local spring_profile=$(get_spring_profile)

    echo "Building the application using Maven with Spring profile: $spring_profile"
    if [ "$build_with_tests" = "yes" ]; then
        (cd "$PROJECT_ROOT_DIR" && mvn clean package -Pwith-tests -Dspring.profiles.active=$spring_profile)
    else
        (cd "$PROJECT_ROOT_DIR" && mvn clean package -Pwithout-tests -Dspring.profiles.active=$spring_profile)
    fi

    if [[ $? -ne 0 ]]; then
        echo "Failed to build the application."
        exit 1
    fi
}

# Check if a parameter is provided for test option
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
