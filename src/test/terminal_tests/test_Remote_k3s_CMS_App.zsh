#!/bin/zsh
set -x
# Basic Auth credentials
USER="user"
PASSWORD="password"

# Since you're using an Ingress, you don't need to fetch the service IP or port.
# Just use the domain configured in your Ingress.
BASE_URL="http://172.16.79.128/cms"
# Add the Host header to curl requests to ensure they are correctly routed by Ingress
HOST_HEADER="Host: myapp.local"
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
    curl -X GET -u ${USER}:${PASSWORD} -H "${HOST_HEADER}" "${BASE_URL}${endpoint}"
    echo "\n"
done

# Loop through IDs for specific endpoints
for (( id=1; id<=MAX_CP_ID; id++ )); do
    curl -X GET -u ${USER}:${PASSWORD} -H "${HOST_HEADER}" "${BASE_URL}/getChargingPoint/${id}"
    echo "\n"
done

for (( id=1; id<=MAX_TARIFF_ID; id++ )); do
    curl -X GET -u ${USER}:${PASSWORD} -H "${HOST_HEADER}" "${BASE_URL}/getTariff/${id}"
    echo "\n"
done

for (( id=1; id<=MAX_SESSION_ID; id++ )); do
    curl -X GET -u ${USER}:${PASSWORD} -H "${HOST_HEADER}" "${BASE_URL}/getChargingSession/${id}"
    echo "\n"
done

for (( id=1; id<=MAX_LOCATION_ID; id++ )); do
    curl -X GET -u ${USER}:${PASSWORD} -H "${HOST_HEADER}" "${BASE_URL}/getLocationInfo/Location${id}/chargePoints"
    echo "\n"
done

# Authentication POST requests
echo "Authentication request..."
curl -u ${USER}:${PASSWORD} -X POST -H "Content-Type: application/json" -d '{ "name": "user1", "password": "password1" }' -H "${HOST_HEADER}" "${BASE_URL}/user/auth"
echo "\n"

# Add POST requests for different commands as needed, similar to the authentication request above.

echo "FINISHED"
