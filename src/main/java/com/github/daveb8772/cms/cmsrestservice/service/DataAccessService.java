package com.github.daveb8772.cms.cmsrestservice.service;


import com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels.*;
import com.github.daveb8772.cms.cmsrestservice.controller.Models.ResponseModels.*;
import com.github.daveb8772.cms.cmsrestservice.dto.*;
import com.github.daveb8772.cms.cmsrestservice.repository.*;
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
    private final ChargingSessionRepository chargingSessionRepository;
    private final ChargingPointRepository chargingPointRepository;
    private final AuthorizationRepository authorizationRepository;
    private final TariffRepository tariffRepository;
    private final LocationInfoRepository locationInfoRepository;

    public DataAccessService(ChargingSessionRepository chargingSessionRepository,
                             ChargingPointRepository chargingPointRepository,
                             AuthorizationRepository authorizationRepository,
                             TariffRepository tariffRepository,
                             LocationInfoRepository locationInfoRepository) {
        this.chargingSessionRepository = chargingSessionRepository;
        this.chargingPointRepository = chargingPointRepository;
        this.authorizationRepository = authorizationRepository;
        this.tariffRepository = tariffRepository;
        this.locationInfoRepository = locationInfoRepository;
    }

    @Transactional(readOnly = true)
    public List<ChargingSessionDataResponse> getChargingSessions() {
        List<ChargingSession> sessions = chargingSessionRepository.findAll();
        return sessions.stream()
                .map(ChargingSessionDTO::fromEntity) // Convert each ChargingSession to ChargingSessionDTO
                .peek(this::initializeChargingSession) // Assuming initializeChargingSession accepts ChargingSessionDTO
                .map(chargingSessionDTO -> new ChargingSessionDataResponse(Collections.singletonList(chargingSessionDTO)))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ChargingSessionDataResponse getChargingSession(String sessionId) {
        List<ChargingSession> sessions = chargingSessionRepository.findAll();
        return sessions.stream()
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
        //Nothing for now
    }



    public AuthorizationResponse authorizeUser() {
        // Fetch the real Authorization entity using the repository
        // For the sake of this example, let's say we fetch the first one
        Authorization authorization = authorizationRepository.findAll().stream().findFirst().orElse(null);

        // Convert the Authorization entity to AuthorizationDTO
        AuthorizationDTO authorizationDTO = authorization != null ? AuthorizationDTO.fromEntity(authorization) : null;

        // Create an AuthorizationResponse with the DTO
        AuthorizationResponse response = new AuthorizationResponse(authorizationDTO);
        response.setStatus(new ResponseStatus(200, "OK")); // Set an appropriate status
        return response;
    }

    @Transactional(readOnly = true)
    public List<ChargingPointDataResponse> getChargingPoints() {
        List<ChargingPoint> chargingPoints = chargingPointRepository.findAll(); // Fetch data from the database
        return chargingPoints.stream()
                .map(ChargingPointDTO::fromEntity) // Convert each ChargingPoint to ChargingPointDTO
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
        return chargingPointRepository.findById(cpId)
                .map(ChargingPointDTO::fromEntity) // Convert to ChargingPointDTO
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


    @Transactional(readOnly = true)
    public List<TariffDataResponse> getTariffs() {
        List<Tariff> tariffs = tariffRepository.findAll(); // Fetch data from the database
        return tariffs.stream()
                .map(TariffDTO::fromEntity) // Convert each Tariff to TariffDTO
                .map(tariffDTO -> {
                    TariffDataResponse response = new TariffDataResponse();
                    response.setTariffs(Collections.singletonList(tariffDTO)); // Wrap in TariffDataResponse
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TariffDataResponse getTariff(String tariffId) {
        return tariffRepository.findById(tariffId)
                .map(TariffDTO::fromEntity) // Convert to TariffDTO
                .map(tariffDTO -> {
                    TariffDataResponse response = new TariffDataResponse();
                    response.setTariffs(Collections.singletonList(tariffDTO)); // Wrap in TariffDataResponse
                    return response;
                })
                .orElse(null); // Return null if no matching tariff is found
    }


    public LocationInfoResponse getLocationInfo() {
        // Assuming you have a LocationInfoRepository to fetch location information
        LocationInfo locationInfo = locationInfoRepository.findById("desired-id").orElse(null);
        LocationInfoDTO locationInfoDTO = locationInfo != null ? LocationInfoDTO.fromEntity(locationInfo) : null;

        // Create a LocationInfoResponse with the DTO
        LocationInfoResponse response = new LocationInfoResponse(locationInfoDTO);
        response.setResponseStatus(new ResponseStatus(200, "OK")); // Set an appropriate status
        return response;
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
