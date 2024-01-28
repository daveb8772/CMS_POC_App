#!/bin/zsh

# Basic Auth credentials
USER="user"
PASSWORD="password"

# Fetch Kubernetes Service IP and Port
SERVICE_NAME="myapp-service" # Update this with your service name
NAMESPACE="default" # Update this with the appropriate namespace if different

#Fetch the external IP or hostname of the service
SERVICE_IP=$(kubectl get svc $SERVICE_NAME --namespace $NAMESPACE --output jsonpath='{.status.loadBalancer.ingress[0].hostname}')

# If no hostname is found, fall back to 'localhost'
if [ -z "$SERVICE_IP" ]; then
    SERVICE_IP="localhost"
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
    curl -X GET -u ${USER}:${PASSWORD} "${BASE_URL}${endpoint}"
    echo "\n"
done

# Loop through IDs for specific endpoints
for (( id=1; id<=MAX_CP_ID; id++ )); do
    curl -X GET -u ${USER}:${PASSWORD} "${BASE_URL}/getChargingPoint/${id}"
    echo "\n"
done

for (( id=1; id<=MAX_TARIFF_ID; id++ )); do
    curl -X GET -u ${USER}:${PASSWORD} "${BASE_URL}/getTariff/${id}"
    echo "\n"
done

for (( id=1; id<=MAX_SESSION_ID; id++ )); do
    curl -X GET -u ${USER}:${PASSWORD} "${BASE_URL}/getChargingSession/${id}"
    echo "\n"
done

for (( id=1; id<=MAX_LOCATION_ID; id++ )); do
    curl -X GET -u ${USER}:${PASSWORD} "${BASE_URL}/getLocationInfo/Location${id}/chargePoints"
    echo "\n"
done

# Authentication POST requests
curl -u ${USER}:${PASSWORD} -X POST -H "Content-Type: application/json" -d '{ "name": "user1", "password": "password1" }' "${BASE_URL}/user/auth"
echo "\n"

# Sending POST requests for different commands
echo "Sending POST request for StartSession..."
curl -u ${USER}:${PASSWORD} -X POST -H "Content-Type: application/json" -d "${userCredentials}" "${K8S_SERVICE_URL}/commands/StartSession"
echo "\n"

echo "Sending POST request for StopSession..."
curl -u ${USER}:${PASSWORD} -X POST -H "Content-Type: application/json" -d "${userCredentials}" "${K8S_SERVICE_URL}/commands/StopSession"
echo "\n"

echo "Sending POST request for Reset..."
curl -u ${USER}:${PASSWORD} -X POST -H "Content-Type: application/json" -d "${userCredentials}" "${K8S_SERVICE_URL}/commands/Reset"
echo "\n"

echo "Sending POST request for UnlockConnector..."
curl -u ${USER}:${PASSWORD} -X POST -H "Content-Type: application/json" -d "${userCredentials}" "${K8S_SERVICE_URL}/commands/UnlockConnector"
echo "\n"

echo "Sending POST request for CancelReservation..."
curl -u ${USER}:${PASSWORD} -X POST -H "Content-Type: application/json" -d "${userCredentials}" "${K8S_SERVICE_URL}/commands/CancelReservation"
echo "\n"

echo "Sending POST request for ReserveNow..."
curl -u ${USER}:${PASSWORD} -X POST -H "Content-Type: application/json" -d "${userCredentials}" "${K8S_SERVICE_URL}/commands/ReserveNow"
echo "\n"
echo "FINISHED"
