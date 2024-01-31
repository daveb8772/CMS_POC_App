#!/bin/zsh

# Get the current context
current_context=$(kubectl config current-context)

# Define the contexts to switch between
context1="docker-desktop"
context2="my-aks-cluster"

# Switch to the other context
if [ "$current_context" = "$context1" ]; then
  kubectl config use-context "$context2"
  echo "Switched to context: $context2"
elif [ "$current_context" = "$context2" ]; then
  kubectl config use-context "$context1"
  echo "Switched to context: $context1"
else
  echo "Current context is neither '$context1' nor '$context2'. No action taken."
fi
