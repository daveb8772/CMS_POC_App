package com.github.daveb8772.ocpi.ocpirestservice.service;

import com.github.daveb8772.ocpi.ocpirestservice.controller.DepotModels.LocationInfo;
import com.github.daveb8772.ocpi.ocpirestservice.controller.ResponseModels.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class OCPIEndpointService {

    @Autowired private DataAccessService dataAccessService;

    public Mono<List<ChargingSessionDataResponse>> getChargingSessions() {
        return Mono.fromCallable(() -> dataAccessService.getChargingSessions());
    }

    public Mono<ChargingSessionDataResponse> getChargingSession(String sessionId) {
        return Mono.fromCallable(() -> dataAccessService.getChargingSession(sessionId));
    }

    public Mono<UserInfoResponse> getUserInfo() {
        return Mono.fromCallable(() -> dataAccessService.getUserInfo());
    }

    public Mono<AuthorizationResponse> authorizeUser() {
        return Mono.fromCallable(() -> dataAccessService.authorizeUser());
    }

    public Mono<List<ChargingPointDataResponse>> getChargingPoints() {
        return Mono.fromCallable(() -> dataAccessService.getChargingPoints());
    }

    public Mono<ChargingPointDataResponse> getChargingPoint(String cpId) {
        long parsedCpId = Long.parseLong(cpId); // Handle NumberFormatException if necessary
        return Mono.fromCallable(() -> dataAccessService.getChargingPoint(String.valueOf(parsedCpId)));
    }

    public Mono<LocationInfo> getLocationInfo() {
        return Mono.fromCallable(() -> dataAccessService.getLocationInfo());
    }


    public SupportedVersionsResponse getSupportedVersions() {
        // This should return the supported versions, possibly by constructing a SupportedVersionsResponse
        // For example:
        return new SupportedVersionsResponse(List.of("2.1.1", "2.2", "2.2.1")); // Example version strings
    }


    public Mono<List<TariffDataResponse>> getTariffs() {
        // Assuming DataAccessService has a method getTariffs()
        return Mono.fromCallable(() -> dataAccessService.getTariffs());
    }

    public Mono<TariffDataResponse> getTariff(String tariffId) {
        // Assuming DataAccessService has a method getTariff(String tariffId)
        return Mono.fromCallable(() -> dataAccessService.getTariff(tariffId));
    }

}
