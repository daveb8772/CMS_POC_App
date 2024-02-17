#!/bin/bash
# file: 4.0.3-setup-remote-prometheus-ingress.sh
set -xe

# Variables for remote server connection
REMOTE_USER="super"
REMOTE_HOST="192.168.2.173"
REMOTE_PORT="2222"
KUBECONFIG_PATH="/home/super/Documents/Software/CMS/yaml/k3s.yaml"
LOCAL_INGRESS_PATH="../k3s/prometheus-ingress.yaml"
REMOTE_INGRESS_PATH="/home/super/Documents/Software/CMS/yaml/prometheus-ingress.yaml"

# SSH command function to avoid repetition
ssh_exec() {
    ssh -t -p "$REMOTE_PORT" "$REMOTE_USER@$REMOTE_HOST" "$@"
}

# Function to setup Helm and Traefik
setup_helm_and_traefik() {
    # Check if Helm is installed
    ssh_exec "which helm || (echo 'Installing Helm...' && curl https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3 | bash)"

    # Add Traefik's Helm repository and update
    ssh_exec "helm repo add traefik https://helm.traefik.io/traefik && helm repo update"

    # Install Traefik via Helm if not already installed
    ssh_exec "helm --kubeconfig $KUBECONFIG_PATH list -n kube-system | grep traefik || helm --kubeconfig $KUBECONFIG_PATH install traefik traefik/traefik --namespace kube-system --create-namespace"
}

# Setup Prometheus and Ingress
setup_prometheus_and_ingress() {
    # Add Prometheus community Helm repository and update
    ssh_exec "helm repo add prometheus-community https://prometheus-community.github.io/helm-charts && helm repo update"

    # Deploy Prometheus using Helm
    ssh_exec "helm --kubeconfig $KUBECONFIG_PATH upgrade --install prometheus prometheus-community/prometheus --namespace monitoring --create-namespace"

    # Copy the ingress.yaml file to the remote server
    scp -P "$REMOTE_PORT" "$LOCAL_INGRESS_PATH" "$REMOTE_USER@$REMOTE_HOST:$REMOTE_INGRESS_PATH"

    # Apply the ingress.yaml configuration
    ssh_exec "kubectl --kubeconfig $KUBECONFIG_PATH apply -f $REMOTE_INGRESS_PATH"
}

echo "*************** Setup Remote Script at REMOTE_HOST=$REMOTE_HOST ****************"
setup_helm_and_traefik
setup_prometheus_and_ingress
echo "Setup completed."
