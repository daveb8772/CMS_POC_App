package com.github.daveb8772.cms.cmsrestservice.controller;

import com.github.daveb8772.cms.cmsrestservice.controller.Models.ResponseModels.ChargingPointDataResponse;
import com.github.daveb8772.cms.cmsrestservice.controller.Models.ResponseModels.LocationInfoResponse;
import com.github.daveb8772.cms.cmsrestservice.service.CMSEndpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Ensure to import your correct Location class
import com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels.LocationInfo; // Replace with your actual Location class import
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/cms")
public class LocationController {

    @Autowired
    private CMSEndpointService CMSEndpointService;



    @GetMapping("/getAllLocationInfo")
    public Mono<ResponseEntity<List<LocationInfoResponse>>> getAllLocationInfo() {
        return CMSEndpointService.getAllLocationInfo()
                .map(locationInfos -> ResponseEntity.ok(locationInfos))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }



    @GetMapping("/getLocationInfo/{name}")
    public Mono<ResponseEntity<LocationInfoResponse>> getLocationInfo(@PathVariable String name) {
        return CMSEndpointService.getLocationInfo(name)
                .map(locationInfo -> ResponseEntity.ok(locationInfo))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/getLocationInfo/{name}/chargePoints")
    public Mono<ResponseEntity<List<ChargingPointDataResponse>>> getChargingPointsByLocation(@PathVariable String name) {
        return CMSEndpointService.getChargingPointsByLocationName(name)
                .flatMapMany(Flux::fromIterable)
                .flatMap(chargingPoint -> new DataAccessResponseHandler<ChargingPointDataResponse>()
                        .handleResponse(Mono.just(chargingPoint), HttpStatus.OK)
                        .map(ResponseEntity::getBody))
                .collectList()
                .flatMap(list -> list.isEmpty()
                        ? Mono.just(ResponseEntity.notFound().build())
                        : Mono.just(ResponseEntity.ok(list)));
    }



}
