#!/bin/zsh

# Variables
REMOTE_USER="super"
REMOTE_SERVER="super"
REMOTE_PORT="2222"
REMOTE_YAML_PATH="/home/super/Documents/Software/CMS/yaml"
KUBECONFIG_PATH="/etc/rancher/k3s/k3s.yaml"
LOCAL_YAML_PATH="/Users/pedroazevedo/Documents/Projects/CMS/CMS_App/k3s"

# Copy YAML files to the remote server
scp -P $REMOTE_PORT $LOCAL_YAML_PATH/*.yaml ${REMOTE_USER}@${REMOTE_SERVER}:$REMOTE_YAML_PATH/

# SSH into the remote server and apply YAML files
ssh -p $REMOTE_PORT ${REMOTE_USER}@${REMOTE_SERVER} << EOF
export KUBECONFIG=${KUBECONFIG_PATH}
kubectl apply -f ${REMOTE_YAML_PATH}/deployment.yaml
kubectl apply -f ${REMOTE_YAML_PATH}/postgres-deployment.yaml
kubectl apply -f ${REMOTE_YAML_PATH}/postgres-pvc.yaml
kubectl apply -f ${REMOTE_YAML_PATH}/postgres-service.yaml
kubectl apply -f ${REMOTE_YAML_PATH}/service.yaml
EOF
