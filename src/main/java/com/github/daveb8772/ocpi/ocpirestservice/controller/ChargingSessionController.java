package com.github.daveb8772.ocpi.ocpirestservice.controller;

import com.github.daveb8772.ocpi.ocpirestservice.controller.Models.ResponseModels.ChargingSessionDataResponse;
import com.github.daveb8772.ocpi.ocpirestservice.service.OCPIEndpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/ocpi") // Base mapping for OCPI endpoints
public class ChargingSessionController {

    @Autowired
    public ChargingSessionController(DataAccessResponseHandler<ChargingSessionDataResponse> dataAccessResponseHandler) {
    }

    @Autowired
    private OCPIEndpointService ocpiEndpointService;


    @GetMapping("/getChargingSessions")
    public Mono<ResponseEntity<List<ChargingSessionDataResponse>>> getChargingSessions() {
        return ocpiEndpointService.getChargingSessions()
                .flatMapMany(Flux::fromIterable)
                .filter(chargingSession -> !chargingSession.isEmpty())
                .flatMap(chargingSession -> new DataAccessResponseHandler<ChargingSessionDataResponse>()
                        .handleResponse(Mono.just(chargingSession), HttpStatus.OK)
                        .map(ResponseEntity::getBody)) // Extract the body from ResponseEntity
                .collectList()
                .flatMap(list -> list.isEmpty()
                        ? Mono.just(new ResponseEntity<>(HttpStatus.NO_CONTENT))
                        : Mono.just(ResponseEntity.ok(list)));
    }

}