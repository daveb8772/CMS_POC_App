package com.github.daveb8772.cms.cmsrestservice.service;


import com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels.*;
import com.github.daveb8772.cms.cmsrestservice.controller.Models.ResponseModels.*;
import com.github.daveb8772.cms.cmsrestservice.dto.*;
import com.github.daveb8772.cms.cmsrestservice.utility.MockDataGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DataAccessService {
    private final List<ChargingSession> mockChargingSessionData;
    private final List<ChargingPoint> mockChargingPointData;
    private final Authorization mockAuthorizationResponse;
    private final List<Tariff> mockTariffData;

    private final LocationInfo mockLocationInfo;

    public DataAccessService() {
        mockChargingSessionData = MockDataGenerator.generateChargingSessions(10);
        mockChargingPointData = MockDataGenerator.generateChargingPoints(5);
        mockAuthorizationResponse = MockDataGenerator.generateAuthorization(true);
        mockTariffData = MockDataGenerator.generateTariffs(5);
        mockLocationInfo = MockDataGenerator.generateLocationInfo();

    }
    @Transactional(readOnly = true)
    public List<ChargingSessionDataResponse> getChargingSessions() {
        return mockChargingSessionData.stream()
                .map(ChargingSessionDTO::fromEntity) // Convert each ChargingSession to ChargingSessionDTO
                .peek(this::initializeChargingSession) // Assuming initializeChargingSession accepts ChargingSessionDTO
                .map(chargingSessionDTO -> new ChargingSessionDataResponse(Collections.singletonList(chargingSessionDTO)))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ChargingSessionDataResponse getChargingSession(String sessionId) {
        return mockChargingSessionData.stream()
                .filter(session -> Objects.equals(session.getSessionId(), sessionId))
                .map(ChargingSessionDTO::fromEntity) // Convert to ChargingSessionDTO
                .peek(this::initializeChargingSession) // Initialize ChargingSessionDTO
                .findFirst()
                .map(chargingSessionDTO -> {
                    ChargingSessionDataResponse response = new ChargingSessionDataResponse(Collections.singletonList(chargingSessionDTO));
                    response.setStatus(new ResponseStatus(200, "OK"));
                    return response;
                })
                .orElse(null);
    }


    private void initializeChargingSession(ChargingSessionDTO chargingSessionDTO) {
    }



    public AuthorizationResponse authorizeUser() {
        // Convert the mock Authorization entity to AuthorizationDTO
        AuthorizationDTO authorizationDTO = AuthorizationDTO.fromEntity(mockAuthorizationResponse);

        // Create an AuthorizationResponse with the DTO
        AuthorizationResponse response = new AuthorizationResponse(authorizationDTO);
        response.setStatus(new ResponseStatus(200, "OK")); // Set an appropriate status
        return response;
    }


    @Transactional(readOnly = true)
    public List<ChargingPointDataResponse> getChargingPoints() {
        return mockChargingPointData.stream()
                .map(ChargingPointDTO::fromEntity) // Convert each ChargingPoint to ChargingPointDTO
                .peek(this::initializeChargingPoint) // Assuming initializeChargingPoint accepts ChargingPointDTO
                .map(chargingPointDTO -> {
                    ChargingPointDataResponse response = new ChargingPointDataResponse();
                    response.setChargingPoints(Collections.singletonList(chargingPointDTO));
                    response.setStatus(new ResponseStatus(200, "OK"));
                    return response;
                })
                .collect(Collectors.toList());
    }


    private void initializeChargingPoint(ChargingPointDTO chargingPointDTO) {
        // Initialize any necessary fields in ChargingPointDTO
        // You'll need to adjust this method to work with ChargingPointDTO
    }


    @Transactional(readOnly = true)
    public ChargingPointDataResponse getChargingPoint(String cpId) {
        return mockChargingPointData.stream()
                .filter(point -> cpId.equals(point.getChargingPointId())) // Filter the ChargingPoint with the given ID
                .map(ChargingPointDTO::fromEntity) // Convert to ChargingPointDTO
                .peek(this::initializeChargingPoint) // Initialize the ChargingPointDTO (assuming the method accepts DTOs)
                .findFirst() // Find the first (and presumably only) matching point
                .map(chargingPointDTO -> {
                    ChargingPointDataResponse response = new ChargingPointDataResponse();
                    response.setChargingPoints(Collections.singletonList(chargingPointDTO));
                    response.setStatus(new ResponseStatus(200, "OK"));
                    return response;
                })
                .orElse(null); // Return null if no matching point is found
    }




    private void initializeChargingPoint(ChargingPoint chargingPoint) {
        if (chargingPoint.getConnectors() != null) {
            chargingPoint.getConnectors().size(); // Initialize dispensers collection
        }
        if (chargingPoint.getChargingProfiles() != null) {
            chargingPoint.getChargingProfiles().size(); // Initialize chargingProfiles collection
        }
        if (chargingPoint.getCommandRequests() != null) {
            chargingPoint.getCommandRequests().size(); // Initialize commandRequests collection
        }

    }


    public List<TariffDataResponse> getTariffs() {
        return mockTariffData.stream()
                .map(TariffDTO::fromEntity) // Convert each Tariff to TariffDTO
                .map(tariffDTO -> {
                    TariffDataResponse response = new TariffDataResponse();
                    response.setTariffs(Collections.singletonList(tariffDTO)); // Wrap in TariffDataResponse
                    return response;
                })
                .collect(Collectors.toList());
    }


    public TariffDataResponse getTariff(String tariffId) {
        return mockTariffData.stream()
                .filter(tariff -> tariff.getTariffId().equals(tariffId)) // Filter the Tariff with the given ID
                .map(TariffDTO::fromEntity) // Convert to TariffDTO
                .findFirst() // Find the first (and presumably only) matching tariff
                .map(tariffDTO -> {
                    TariffDataResponse response = new TariffDataResponse();
                    response.setTariffs(Collections.singletonList(tariffDTO)); // Wrap in TariffDataResponse
                    return response;
                })
                .orElse(null); // Return null if no matching tariff is found
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
