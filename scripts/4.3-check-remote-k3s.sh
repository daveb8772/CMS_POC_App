#!/bin/zsh
set -x

# Variables
REMOTE_USER="super"
REMOTE_SERVER="super"
REMOTE_PORT="2222"
KUBECONFIG_PATH="/home/super/Documents/Software/CMS/yaml/k3s.yaml"

# Check the status of all resources in the remote K3s cluster
#ssh -p $REMOTE_PORT ${REMOTE_USER}@${REMOTE_SERVER} "export KUBECONFIG=${KUBECONFIG_PATH} && kubectl get all"
ssh -p $REMOTE_PORT ${REMOTE_USER}@${REMOTE_SERVER} "kubectl --kubeconfig=${KUBECONFIG_PATH} get all"
