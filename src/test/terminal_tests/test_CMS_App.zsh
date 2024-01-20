#!/bin/zsh

# Basic Auth credentials
USER="user"
PASSWORD="password"

# Base URL of your API
BASE_URL="http://localhost:8081/cms"

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
# Array of commands
commands=(
    "StartSession"
    "StopSession"
    "Reset"
    "UnlockConnector"
    "CancelReservation"
    "ReserveNow"
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
 #echo "Sending /getChargingSession/id"
for (( id=1; id<=MAX_SESSION_ID; id++ )); do
    curl -X GET -u ${USER}:${PASSWORD} "${BASE_URL}/getChargingSession/${id}"
    echo "\n"
done

 #echo "Sending /getLocationInfo/Location id /chargePoints"
for (( id=1; id<=MAX_LOCATION_ID; id++ )); do
    curl -X GET -u ${USER}:${PASSWORD} "${BASE_URL}/getLocationInfo/Location${id}/chargePoints"
    echo "\n"
done

# Authentication POST requests
curl -u  ${USER}:${PASSWORD} -X POST -H "Content-Type: application/json"  -d '{ "name": "user1", "password": "password1" }' "${BASE_URL}/user/auth"
echo "\n"
# Command POST requests
curl -u  ${USER}:${PASSWORD} -X POST -H "Content-Type: application/json"  -d '{ "name": "user1", "password": "password1" }' "${BASE_URL}/commands/StartSession"
echo "\n"
curl -u  ${USER}:${PASSWORD} -X POST -H "Content-Type: application/json"  -d '{ "name": "user1", "password": "password1" }' "${BASE_URL}/commands/StopSession"
echo "\n"
curl -u  ${USER}:${PASSWORD} -X POST -H "Content-Type: application/json"  -d '{ "name": "user1", "password": "password1" }' "${BASE_URL}/commands/Reset"
echo "\n"
curl -u  ${USER}:${PASSWORD} -X POST -H "Content-Type: application/json"  -d '{ "name": "user1", "password": "password1" }' "${BASE_URL}/commands/UnlockConnector"
echo "\n"
curl -u  ${USER}:${PASSWORD} -X POST -H "Content-Type: application/json"  -d '{ "name": "user1", "password": "password1" }' "${BASE_URL}/commands/CancelReservation"
echo "\n"
curl -u  ${USER}:${PASSWORD} -X POST -H "Content-Type: application/json"  -d '{ "name": "user1", "password": "password1" }' "${BASE_URL}/commands/ReserveNow"
echo "\n"


echo "FINISHED"
