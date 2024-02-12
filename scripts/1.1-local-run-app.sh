#!/bin/bash

# Define the root directory of the Maven project
PROJECT_ROOT_DIR="../target" # Adjust this to the root directory of your Maven project

# Find the latest JAR file in the target directory
JAR_FILE=$(ls -t $PROJECT_ROOT_DIR/cms_app-*.jar | head -1)

# Database configuration for local development
LOCAL_DB_URL="jdbc:postgresql://localhost:5432/CMS_Data"

# Run the application with the local database configuration
java -jar "$JAR_FILE" --spring.datasource.url="$LOCAL_DB_URL"
