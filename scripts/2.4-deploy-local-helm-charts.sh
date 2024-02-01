#!/bin/zsh

# Set the namespace variable, or use default if not set
NAMESPACE=${1:-default}

# Path to the directory where your Helm chart for Prometheus is located
HELM_CHART_DIR="../k8s/prometheus-setup"

# Navigate to the Helm chart directory
cd $HELM_CHART_DIR

# Kill existing Prometheus setup pod to free up port 9090
echo "Checking for existing Prometheus setup..."
EXISTING_POD=$(kubectl get pods --namespace $NAMESPACE -l "app.kubernetes.io/name=prometheus-setup,app.kubernetes.io/instance=prometheus-setup" -o jsonpath="{.items[0].metadata.name}" 2> /dev/null)
if [ ! -z "$EXISTING_POD" ]; then
    echo "Found existing Prometheus setup. Deleting pod $EXISTING_POD..."
    kubectl delete pod --namespace $NAMESPACE $EXISTING_POD
    sleep 5  # Wait for a few seconds to ensure the pod is terminated
fi

# Install or upgrade Prometheus Helm chart
echo "Deploying Prometheus setup..."
helm upgrade --install prometheus-setup . \
  --namespace $NAMESPACE \
  --create-namespace \
  --values values.yaml

# Export POD_NAME using the Helm release name
export POD_NAME=$(kubectl get pods --namespace $NAMESPACE -l "app.kubernetes.io/name=prometheus-setup,app.kubernetes.io/instance=prometheus-setup" -o jsonpath="{.items[0].metadata.name}")

# Wait for the new pod to be ready
echo "Waiting for the new Prometheus pod to be ready..."
kubectl wait --for=condition=ready pod --namespace $NAMESPACE $POD_NAME --timeout=60s

# Check if port 9090 is already in use
if lsof -i:9090; then
    echo "Port 9090 is already in use. Attempting to free it up..."
    PID=$(lsof -ti:9090)
    if [ ! -z "$PID" ]; then
        echo "Killing process $PID using port 9090..."
        kill -9 $PID
    fi
fi

# Port-forward
echo "Setting up port-forwarding to Prometheus..."
kubectl --namespace $NAMESPACE port-forward $POD_NAME 9090:9090 &

# Provide the user with the application URL
echo "Visit http://127.0.0.1:9090 to use your application"

# Navigate back to the scripts directory
cd -

echo "Prometheus has been deployed/updated in the $NAMESPACE namespace."
