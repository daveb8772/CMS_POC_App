package com.github.daveb8772.ocpi.ocpirestservice.service;

import com.github.daveb8772.ocpi.ocpirestservice.controller.Models.DepotModels.Tariff;
import com.github.daveb8772.ocpi.ocpirestservice.controller.Models.DepotModels.CommandRequest;
import com.github.daveb8772.ocpi.ocpirestservice.controller.Models.DepotModels.LocationInfo;
import com.github.daveb8772.ocpi.ocpirestservice.controller.Models.ResponseModels.*;
import com.github.daveb8772.ocpi.ocpirestservice.controller.ResponseModels.*;
import com.github.daveb8772.ocpi.ocpirestservice.utility.MockDataGenerator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

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

    public List<ChargingSessionDataResponse> getChargingSessions() {
        return mockChargingSessionData;
    }

    public ChargingSessionDataResponse getChargingSession(String sessionId) {
        return mockChargingSessionData.stream()
                .filter(chargingSessionDataResponse -> chargingSessionDataResponse.getChargingSessions().stream()
                        .anyMatch(session -> Objects.equals(session.getSessionId(), sessionId)))
                .findFirst().orElse(null);
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
