#!/bin/zsh

echo "Listing Kubernetes nodes, pods, and services..."
kubectl get nodes
kubectl get pods
kubectl get svc

# Fetch the latest pod name for the database and application
DB_POD=$(kubectl get pods -l app=postgres -o jsonpath="{.items[0].metadata.name}")
APP_POD=$(kubectl get pods -l app=myapp -o jsonpath="{.items[0].metadata.name}")

# Check if the pods were found
if [ -z "$DB_POD" ]; then
    echo "Database pod not found."
else
    echo "Logs for Database Pod ($DB_POD):"
    kubectl logs "$DB_POD"
fi

if [ -z "$APP_POD" ]; then
    echo "Application pod not found."
else
    echo "Logs for Application Pod ($APP_POD):"
    kubectl logs "$APP_POD"
    kubectl describe pod "$APP_POD"

fi


