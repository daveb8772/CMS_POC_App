package com.github.daveb8772.cms.cmsrestservice;


import com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels.CommandRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

class CMSApplicationTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;

    @BeforeEach
    public void setup() {
        this.baseUrl = "http://localhost:" + port + "/cms/";
    }

    @Test
    public void testListEndpoints() {
        ResponseEntity<String> response = restTemplate.withBasicAuth("user", "password")
                .getForEntity(baseUrl + "listEndpoints", String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        // Replace with actual expected endpoint names
        assertThat(response.getBody()).contains("/cms/getLocationInfo/{name}/chargePoints", "/cms/getAllLocationInfo");

    }

    @Test
    public void testGetChargingPoints() {
        ResponseEntity<String> response = restTemplate.withBasicAuth("user", "password")
                .getForEntity(baseUrl + "getChargingPoints", String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        // Adapt this to expected data
        assertThat(response.getBody()).contains("chargingPointId", "status", "connector");
    }

    // Utility method to retrieve charging point IDs
    private List<Integer> getChargingPointIds() throws JSONException {
        ResponseEntity<String> response = restTemplate.withBasicAuth("user", "password")
                .getForEntity(baseUrl + "getChargingPoints", String.class);
        List<Integer> ids = new ArrayList<>();
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            JSONArray rootArray = new JSONArray(response.getBody());
            for (int i = 0; i < rootArray.length(); i++) {
                JSONArray chargingPointsArray = rootArray.getJSONObject(i).getJSONArray("chargingPoints");
                for (int j = 0; j < chargingPointsArray.length(); j++) {
                    int cpId = chargingPointsArray.getJSONObject(j).getInt("chargingPointId");
                    ids.add(cpId);
                }
            }
        }
        return ids;
    }


    @Test
    public void testGetChargingPointDetails() throws JSONException {
        // Fetch charging point IDs
        List<Integer> ids = getChargingPointIds();
        if (!ids.isEmpty()) {
            Integer cpId = ids.get(0); // Use the first ID for testing
            ResponseEntity<String> response = restTemplate.withBasicAuth("user", "password")
                    .getForEntity(baseUrl + "getChargingPoint/" + cpId, String.class);
            assertThat(response.getStatusCodeValue()).isEqualTo(200);
            assertThat(response.getBody()).contains("chargingPointId", "status");
        } else {
            System.out.println("No charging point IDs found");
        }
    }

    @Test
    public void testGetTariffs() {
        ResponseEntity<String> response = restTemplate.withBasicAuth("user", "password")
                .getForEntity(baseUrl + "getTariffs", String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).contains("tariffId", "tariffName", "price");
    }

    @Test
    public void testUserAuthEndpoint() {
        ResponseEntity<String> response = restTemplate.withBasicAuth("user", "password")
                .getForEntity(baseUrl + "user/auth", String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(405); // Replace with actual expected status
        assertThat(response.getBody()).contains("Method Not Allowed");
    }

    @Test
    public void testErrorEndpoint() {
        ResponseEntity<String> response = restTemplate.withBasicAuth("user", "password")
                .getForEntity(baseUrl + "error", String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(404); // Replace with actual expected status
        assertThat(response.getBody()).contains("Not Found");
    }

    @Test
    public void testTestEndpoint() {
        ResponseEntity<String> response = restTemplate.withBasicAuth("user", "password")
                .getForEntity(baseUrl + "test", String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).contains("Service is running!");
    }

    @Test
    public void testGetAllLocationInfo() {
        ResponseEntity<String> response = restTemplate.withBasicAuth("user", "password")
                .getForEntity(baseUrl + "getAllLocationInfo", String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).contains("locationInfo", "locationId", "locationAddress");
    }


    @Test
    public void testGetChargingSessions() {
        ResponseEntity<String> response = restTemplate.withBasicAuth("user", "password")
                .getForEntity(baseUrl + "getChargingSessions", String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).contains("chargingSessions", "sessionId", "status");
    }


    // Utility method to retrieve charging session IDs
    private List<String> getChargingSessionIds() throws JSONException {
        ResponseEntity<String> response = restTemplate.withBasicAuth("user", "password")
                .getForEntity(baseUrl + "getChargingSessions", String.class);
        List<String> ids = new ArrayList<>();
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            // Parse the JSON response body
            JSONArray sessionsArray = new JSONArray(response.getBody());

            // Iterate over each session object
            for (int i = 0; i < sessionsArray.length(); i++) {
                // Get the individual session object
                JSONObject sessionObject = sessionsArray.getJSONObject(i).getJSONArray("chargingSessions").getJSONObject(0);

                // Extract the sessionId and add it to the list
                String sessionId = sessionObject.getString("sessionId");
                ids.add(sessionId);
            }
        }
        return ids;
    }


    @Test
    public void testGetChargingSessionDetails() throws JSONException {
        // Fetch charging session IDs
        List<String> ids = getChargingSessionIds();
        if (!ids.isEmpty()) {
            String sessionId = ids.get(0); // Use the first ID for testing
            ResponseEntity<String> response = restTemplate.withBasicAuth("user", "password")
                    .getForEntity(baseUrl + "getChargingSession/" + sessionId, String.class);
            assertThat(response.getStatusCodeValue()).isEqualTo(200);
            assertThat(response.getBody()).contains("id", "cpId", "status");
        } else {
            System.out.println("No charging session IDs found");
        }
    }
    private List<String> getLocationNames() throws JSONException {
        ResponseEntity<String> response = restTemplate.withBasicAuth("user", "password")
                .getForEntity(baseUrl + "getAllLocationInfo", String.class);
        List<String> locationNames = new ArrayList<>();

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            JSONArray locationInfos = new JSONArray(response.getBody()); // Directly parse the body as a JSONArray
            for (int i = 0; i < locationInfos.length(); i++) {
                JSONObject locationInfo = locationInfos.getJSONObject(i).getJSONObject("locationInfo");
                String locationName = locationInfo.getString("name");
                locationNames.add(locationName);
            }
        }
        return locationNames;
    }

    @Test
    public void testGetLocationInfoWithChargePoints() throws JSONException {
        List<String> locationNames = getLocationNames();
        if (!locationNames.isEmpty()) {
            String locationName = locationNames.get(0); // Use the first location name for testing
            ResponseEntity<String> response = restTemplate.withBasicAuth("user", "password")
                    .getForEntity(baseUrl + "getLocationInfo/{name}/chargePoints", String.class, locationName);
            assertThat(response.getStatusCodeValue()).isEqualTo(200);
            assertThat(response.getBody()).contains("chargingPointId", "status");
        } else {
           System.out.println("No location names found");
        }
    }

    private List<Integer> getTariffIds() throws JSONException {
        ResponseEntity<String> response = restTemplate.withBasicAuth("user", "password")
                .getForEntity(baseUrl + "getTariffs", String.class);
        List<Integer> tariffIds = new ArrayList<>();

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            JSONArray tariffsArray = new JSONArray(response.getBody()); // Directly parse the body as a JSONArray
            for (int i = 0; i < tariffsArray.length(); i++) {
                JSONArray tariffs = tariffsArray.getJSONObject(i).getJSONArray("tariffs");
                for (int j = 0; j < tariffs.length(); j++) {
                    int tariffId = tariffs.getJSONObject(j).getInt("tariffId");
                    tariffIds.add(tariffId);
                }
            }
        }
        return tariffIds;
    }



    @Test
    public void testGetLocationInfoByName() throws JSONException {
        List<String> locationNames = getLocationNames();
        // Now use the location name to replace the placeholder in the URI template
        // Example for the first location name
        if (!locationNames.isEmpty()) {
            String locationName = locationNames.get(0);
            ResponseEntity<String> response = restTemplate.withBasicAuth("user", "password")
                    .getForEntity(baseUrl + "getLocationInfo/" + locationName, String.class);
            assertThat(response.getStatusCodeValue()).isEqualTo(200);
            assertThat(response.getBody()).contains("locationInfo", "locationId");
        } else {
            System.out.println("No location names found");
        }
    }

    @Test
    public void testGetTariffDetails() throws JSONException {
        List<Integer> tariffIds = getTariffIds();
        // Now use the tariff ID to replace the placeholder in the URI template
        // Example for the first tariff ID
        if (!tariffIds.isEmpty()) {
            Integer tariffId = tariffIds.get(0);
            ResponseEntity<String> response = restTemplate.withBasicAuth("user", "password")
                    .getForEntity(baseUrl + "getTariff/" + tariffId, String.class);
            assertThat(response.getStatusCodeValue()).isEqualTo(200);
            assertThat(response.getBody()).contains("tariffId", "tariffName", "price");
        } else {
            System.out.println("No tariff IDs found");
        }
    }

    @Test
    public void testGetCommands() {
        ResponseEntity<String> response = restTemplate.withBasicAuth("user", "password")
                .getForEntity(baseUrl + "commands/getCommands", String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).contains("result", "status");
    }

    @Test
    public void testSendCommand() {
        // Create a CommandRequest object with the necessary details
        CommandRequest commandRequest = new CommandRequest();
        commandRequest.setCommandType(CommandRequest.CommandType.START_SESSION); // Example command type
        commandRequest.setCommandParameter("someParameter"); // Example command parameter

        HttpEntity<CommandRequest> request = new HttpEntity<>(commandRequest);

        ResponseEntity<String> response = restTemplate.withBasicAuth("user", "password")
                .postForEntity(baseUrl + "commands/StartSession", request, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200); // Change if required
        assertThat(response.getBody()).contains("result", "status");
    }


}
