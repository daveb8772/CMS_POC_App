#!/bin/zsh

echo "Listing Kubernetes nodes, pods, and services..."
kubectl get nodes
kubectl get pods
kubectl get pods -n kube-system
kubectl get svc

# Fetch the latest pod name for the database and application
DB_POD=$(kubectl get pods --selector=app=postgres --output=jsonpath='{.items[0].metadata.name}')
APP_POD=$(kubectl get pods --selector=app=myapp --output=jsonpath='{.items[0].metadata.name}')
NAMESPACE=$(kubectl config view --minify --output 'jsonpath={..namespace}')

# Check if the pods and namespace were found
if [ -z "$DB_POD" ]; then
    echo "Database pod not found."
else
    #echo "Logs for Database Pod ($DB_POD):"
    #kubectl logs "$DB_POD" --namespace=$NAMESPACE
    echo "Resource Usage for Database Pod ($DB_POD):"
    kubectl top pod "$DB_POD" --namespace=$NAMESPACE
    echo "Description for Database Pod ($DB_POD):"
    kubectl describe pod "$DB_POD" --namespace=$NAMESPACE
fi

if [ -z "$APP_POD" ]; then
    echo "Application pod not found."
else
    #echo "Logs for Application Pod ($APP_POD):"
    #kubectl logs "$APP_POD" --namespace=$NAMESPACE
    echo "Resource Usage for Application Pod ($APP_POD):"
    kubectl top pod "$APP_POD" --namespace=$NAMESPACE
    echo "Description for Application Pod ($APP_POD):"
    kubectl describe pod "$APP_POD" --namespace=$NAMESPACE
fi
