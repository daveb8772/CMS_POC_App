#!/bin/zsh

# Basic Auth credentials
USER="user"
PASSWORD="password"

# Fetch Kubernetes Service IP and Port
SERVICE_NAME="myapp-service" # Update this with your service name
NAMESPACE="default" # Update this with the appropriate namespace if different

# Fetch the external IP or hostname of the service
SERVICE_IP=$(kubectl get svc $SERVICE_NAME --namespace $NAMESPACE --output jsonpath='{.status.loadBalancer.ingress[0].ip}')

# If no IP is found, try to fetch hostname (useful for certain cloud setups)
if [ -z "$SERVICE_IP" ]; then
    SERVICE_IP=$(kubectl get svc $SERVICE_NAME --namespace $NAMESPACE --output jsonpath='{.status.loadBalancer.ingress[0].hostname}')
fi

# In case SERVICE_IP is still not set, exit with error message
if [ -z "$SERVICE_IP" ]; then
    echo "Error: Failed to obtain External IP or Hostname for $SERVICE_NAME"
    exit 1
fi

# Fetch the port of the service
SERVICE_PORT=$(kubectl get svc $SERVICE_NAME --namespace $NAMESPACE --output jsonpath='{.spec.ports[0].port}')

# If no port is found, fall back to default HTTP port
if [ -z "$SERVICE_PORT" ]; then
    SERVICE_PORT=80
fi

# Construct the Base URL
K8S_SERVICE_URL="http://${SERVICE_IP}:${SERVICE_PORT}"
BASE_URL="${K8S_SERVICE_URL}/cms"
# Maximum ID values for each type of ID
MAX_CP_ID=15 # Max Charging Point ID
MAX_TARIFF_ID=5 # Max Tariff ID
MAX_SESSION_ID=4 # Max Session ID
MAX_LOCATION_ID=3 # Max Location ID

# Array of endpoints
endpoints=(
    "/listEndpoints"
    "/getChargingPoints"
    "/getTariffs"
    "/user/auth"
    "/error"
    "/test"
    "/getAllLocationInfo"
    "/error"
    "/commands/getCommands"
)

# Call each endpoint in the array
for endpoint in "${endpoints[@]}"; do
    echo "Requesting ${BASE_URL}${endpoint}..."
    curl -u ${USER}:${PASSWORD} -X GET "${BASE_URL}${endpoint}"
    echo "\n"
done

# Loop through IDs for specific endpoints
for (( id=1; id<=MAX_CP_ID; id++ )); do
    curl -u ${USER}:${PASSWORD} -X GET "${BASE_URL}/getChargingPoint/${id}"
    echo "\n"
done

for (( id=1; id<=MAX_TARIFF_ID; id++ )); do
    curl -u ${USER}:${PASSWORD} -X GET "${BASE_URL}/getTariff/${id}"
    echo "\n"
done

for (( id=1; id<=MAX_SESSION_ID; id++ )); do
    curl -u ${USER}:${PASSWORD} -X GET "${BASE_URL}/getChargingSession/${id}"
    echo "\n"
done

for (( id=1; id<=MAX_LOCATION_ID; id++ )); do
    curl -u ${USER}:${PASSWORD} -X GET "${BASE_URL}/getLocationInfo/Location${id}/chargePoints"
    echo "\n"
done

# Authentication and Commands POST requests might need adjustments based on your application's requirements
