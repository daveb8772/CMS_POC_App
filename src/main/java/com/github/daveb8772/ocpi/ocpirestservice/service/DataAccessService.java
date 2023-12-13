package com.github.daveb8772.ocpi.ocpirestservice.service;


import com.github.daveb8772.ocpi.ocpirestservice.controller.Models.DepotModels.*;
import com.github.daveb8772.ocpi.ocpirestservice.controller.Models.ChargeModels.*;
import com.github.daveb8772.ocpi.ocpirestservice.controller.Models.ResponseModels.*;
import com.github.daveb8772.ocpi.ocpirestservice.utility.MockDataGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DataAccessService {
    private final List<ChargingSessionDataResponse> mockChargingSessionData;
    private final List<ChargingPointDataResponse> mockChargingPointData;
    private final AuthorizationResponse mockAuthorizationResponse;
    private final List<TariffDataResponse> mockTariffData;

    private final LocationInfo mockLocationInfo;

    public DataAccessService() {
        mockChargingSessionData = MockDataGenerator.generateChargingSessions(10);
        mockChargingPointData = MockDataGenerator.generateChargingPoints(5);
        mockAuthorizationResponse = MockDataGenerator.generateAuthorizationResponse(true);
        mockTariffData = MockDataGenerator.generateTariffs(5);
        mockLocationInfo = MockDataGenerator.generateLocationInfo();

    }
    @Transactional(readOnly = true)
    public List<ChargingSessionDataResponse> getChargingSessions() {
        List<ChargingSessionDataResponse> sessions = mockChargingSessionData;
        // Initialize any lazy collections within each ChargingSession in ChargingSessionDataResponse
        sessions.forEach(dataResponse -> dataResponse.getChargingSessions().forEach(this::initializeChargingSession));
        return sessions;
    }

    @Transactional(readOnly = true)
    public ChargingSessionDataResponse getChargingSession(String sessionId) {
        return mockChargingSessionData.stream()
                .filter(dataResponse -> dataResponse.getChargingSessions().stream()
                        .anyMatch(session -> {
                            if (Objects.equals(session.getSessionId(), sessionId)) {
                                initializeChargingSession(session); // Initialize lazy-loaded collections
                                return true;
                            }
                            return false;
                        }))
                .findFirst().orElse(null);
    }

    private void initializeChargingSession(ChargingSession session) {
        if (session.getErrors() != null) {
            session.getErrors().size(); // Initialize errors collection
        }
        if (session.getTariffChanges() != null) {
            session.getTariffChanges().size(); // Initialize tariffChanges collection
        }
        if (session.getMeterRecords() != null) {
            session.getMeterRecords().size(); // Initialize meterRecords collection
        }
        // Repeat for any other lazy-loaded collections
    }


    public AuthorizationResponse authorizeUser() {
        // Returning the first mock authorization response
        return mockAuthorizationResponse;
    }



    @Transactional(readOnly = true)
    public List<ChargingPointDataResponse> getChargingPoints() {
        // Initialize each ChargingPoint within the ChargingPointDataResponse
        return mockChargingPointData.stream()
                .peek(dataResponse -> dataResponse.getChargingPoints().forEach(this::initializeChargingPoint))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ChargingPointDataResponse getChargingPoint(String cpId) {
        return mockChargingPointData.stream()
                .filter(dataResponse -> dataResponse.getChargingPoints().stream()
                        .anyMatch(point -> {
                            if (cpId.equals(point.getChargingPointId())) {
                                initializeChargingPoint(point); // Initialize lazy-loaded collections
                                return true;
                            }
                            return false;
                        }))
                .findFirst()
                .orElse(null);
    }

    private void initializeChargingPoint(ChargingPoint chargingPoint) {
        if (chargingPoint.getDispensers() != null) {
            chargingPoint.getDispensers().size(); // Initialize dispensers collection
        }
        if (chargingPoint.getChargingProfiles() != null) {
            chargingPoint.getChargingProfiles().size(); // Initialize chargingProfiles collection
        }
        if (chargingPoint.getCommandRequests() != null) {
            chargingPoint.getCommandRequests().size(); // Initialize commandRequests collection
        }
        // Repeat for any other lazy-loaded collections
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
    public CommandResponse commandRequest(String command, CommandRequest request) {
        CommandResponse commandResponse = new CommandResponse();
        ResponseStatus responseStatus = new ResponseStatus();

        switch (request.getCommandType()) {
            case START_SESSION -> {
                // Handle start session command
                responseStatus.setCode(200);
                responseStatus.setMessage("Session started successfully");
                commandResponse.setStatus(responseStatus);
                commandResponse.setResult("SessionId: " + UUID.randomUUID().toString());
            }
            case STOP_SESSION -> {
                // Handle stop session command
                responseStatus.setCode(200);
                responseStatus.setMessage("Session stopped successfully");
                commandResponse.setStatus(responseStatus);
                commandResponse.setResult("SessionId: " + request.getCommandParameter() + " stopped");
            }
            case UNLOCK_CONNECTOR -> {
                // Handle unlock connector command
                responseStatus.setCode(200);
                responseStatus.setMessage("Connector unlocked successfully");
                commandResponse.setStatus(responseStatus);
                commandResponse.setResult("ConnectorId: " + request.getCommandParameter() + " unlocked");
            }
            case CANCEL_RESERVATION -> {
                // Handle cancel reservation command
                responseStatus.setCode(200);
                responseStatus.setMessage("Reservation cancelled successfully");
                commandResponse.setStatus(responseStatus);
                commandResponse.setResult("ReservationId: " + request.getCommandParameter() + " cancelled");
            }
            case RESERVE_NOW -> {
                // Handle reserve now command
                responseStatus.setCode(200);
                responseStatus.setMessage("Reservation made successfully");
                commandResponse.setStatus(responseStatus);
                commandResponse.setResult("ReservationId: " + UUID.randomUUID().toString());
            }
            default -> {
                responseStatus.setCode(400);
                responseStatus.setMessage("Unsupported command");
                commandResponse.setStatus(responseStatus);
                commandResponse.setResult("Command not supported");
            }
        }

        return commandResponse;
    }
}
