# Use a base image with JDK 17
#FROM openjdk:17.0.9-slim as build
FROM eclipse-temurin:17-jdk as build
# Copy the jar file into the container
COPY target/*.jar myapp.jar

# Run the application
ENTRYPOINT ["java","-jar","/myapp.jar"]
