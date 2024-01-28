#!/bin/zsh

echo "Listing Kubernetes nodes, pods, and kube-system"

kubectl get nodes
kubectl get pods
kubectl get pods -n kube-system
kubectl get svc
