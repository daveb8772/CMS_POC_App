package com.github.daveb8772.cms.cmsrestservice.controller;

import com.github.daveb8772.cms.cmsrestservice.Security.SecurityConfiguration;
import com.github.daveb8772.cms.cmsrestservice.controller.Models.ResponseModels.ChargingSessionDataResponse;
import com.github.daveb8772.cms.cmsrestservice.service.CMSEndpointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/cms") // Base mapping for CMS endpoints
public class ChargingSessionController {

    @Autowired
    public ChargingSessionController(DataAccessResponseHandler<ChargingSessionDataResponse> dataAccessResponseHandler) {
    }

    @Autowired
    private CMSEndpointService CMSEndpointService;

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

    @GetMapping("/getChargingSessions")
    public Mono<ResponseEntity<List<ChargingSessionDataResponse>>> getChargingSessions() {
        logger.info("/getChargingSessions called");
        return CMSEndpointService.getChargingSessions()
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

    @GetMapping("/getChargingSession/{id}")
    public Mono<ResponseEntity<ChargingSessionDataResponse>> getChargingSession(@PathVariable String id) {
        logger.info("/getChargingSession/{id} called");
        return CMSEndpointService.getChargingSessionById(id)
                .flatMap(chargingSession -> chargingSession != null
                        ? Mono.just(ResponseEntity.ok(chargingSession))
                        : Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }

}