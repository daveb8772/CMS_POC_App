package com.github.daveb8772.ocpi.ocpirestservice.service;

import com.github.daveb8772.ocpi.ocpirestservice.controller.ChargeModels.Tariff;
import com.github.daveb8772.ocpi.ocpirestservice.controller.DepotModels.LocationInfo;
import com.github.daveb8772.ocpi.ocpirestservice.controller.ResponseModels.*;
import com.github.daveb8772.ocpi.ocpirestservice.utility.MockDataGenerator;

import java.util.List;
import java.util.Objects;

public class DataAccessService {
    private final List<ChargingSessionDataResponse> mockChargingSessionData;
    private final List<ChargingPointDataResponse> mockChargingPointData;
    private final UserInfoResponse mockUserInfo;
    private final AuthorizationResponse mockAuthorizationResponse;
    private final List<TariffDataResponse> mockTariffData;

    private final LocationInfo mockLocationInfo;

    public DataAccessService() {
        mockChargingSessionData = MockDataGenerator.generateChargingSessions(10);
        mockChargingPointData = MockDataGenerator.generateChargingPoints(5);
        mockUserInfo = MockDataGenerator.generateUserInfo("user1");
        mockAuthorizationResponse = MockDataGenerator.generateAuthorizationResponse(true);
        mockTariffData = MockDataGenerator.generateTariffs(5);
        mockLocationInfo = MockDataGenerator.generateLocationInfo();

    }

    public List<ChargingSessionDataResponse> getChargingSessions() {
        return mockChargingSessionData;
    }

    public ChargingSessionDataResponse getChargingSession(String sessionId) {
        return mockChargingSessionData.stream()
                .filter(chargingSessionDataResponse -> chargingSessionDataResponse.getChargingSessions().stream()
                        .anyMatch(session -> Objects.equals(session.getSessionId(), sessionId)))
                .findFirst().orElse(null);
    }


    public UserInfoResponse getUserInfo() {
        // Assuming you want to return user1's info for any request
        return mockUserInfo;
    }

    public AuthorizationResponse authorizeUser() {
        // Returning the first mock authorization response
        return mockAuthorizationResponse;
    }

    public List<ChargingPointDataResponse> getChargingPoints() {
        return mockChargingPointData;
    }

    public ChargingPointDataResponse getChargingPoint(String cpId) {
        return mockChargingPointData.stream()
                .filter(chargingPointDataResponse -> chargingPointDataResponse.getChargingPoints().stream()
                        .anyMatch(point -> Objects.equals(point.getChargingPointId(), cpId)))
                .findFirst().orElse(null);
    }


    public List<TariffDataResponse> getTariffs() {
        return mockTariffData;
    }

    public TariffDataResponse getTariff(String tariffId) {
        for (TariffDataResponse tariffDataResponse : mockTariffData) {
            for (Tariff tariff : tariffDataResponse.getTariffs()) {
                if (tariff.getTariffId().equals(tariffId)) {
                    // Returning a new TariffDataResponse containing only the matched tariff
                    TariffDataResponse response = new TariffDataResponse();
                    response.setTariffs(List.of(tariff));
                    return response;
                }
            }
        }
        return null; // Tariff not found
    }

    public LocationInfo getLocationInfo() {
        // Fetch and return the LocationInfo
        // This is a placeholder - you need to implement the logic to retrieve the actual location info
        return mockLocationInfo;
    }

}
