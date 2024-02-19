#!/bin/zsh
# file: 4.6-check-remote-k3s.sh
set -x

# Variables
REMOTE_USER="super"
REMOTE_SERVER="192.168.2.173"
REMOTE_PORT="2222"
KUBECONFIG_PATH="/home/super/Documents/Software/CMS/yaml/k3s.yaml"

# Fetch all resources from the K3s cluster
ssh -p $REMOTE_PORT ${REMOTE_USER}@${REMOTE_SERVER} "kubectl --kubeconfig=${KUBECONFIG_PATH} get all"

# Assuming your myapp pods have a consistent label, like app=myapp
# Fetch the name of the first pod for the myapp deployment
MYAPP_POD=$(ssh -p $REMOTE_PORT ${REMOTE_USER}@${REMOTE_SERVER} "kubectl --kubeconfig=${KUBECONFIG_PATH} get pods -l app=myapp -o jsonpath='{.items[0].metadata.name}'")

# Fetch logs of the myapp pod
ssh -p $REMOTE_PORT ${REMOTE_USER}@${REMOTE_SERVER} "kubectl --kubeconfig=${KUBECONFIG_PATH} logs $MYAPP_POD"
