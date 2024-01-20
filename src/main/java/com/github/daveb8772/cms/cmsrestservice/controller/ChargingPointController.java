package com.github.daveb8772.cms.cmsrestservice.controller;

import com.github.daveb8772.cms.cmsrestservice.controller.Models.ResponseModels.ChargingPointDataResponse;
import com.github.daveb8772.cms.cmsrestservice.service.CMSEndpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/cms")
public class ChargingPointController {

    @Autowired
    private CMSEndpointService CMSEndpointService;




    @GetMapping(value="/getChargingPoints", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<ChargingPointDataResponse>>> getChargingPoints() {
        return CMSEndpointService.getChargingPoints()
                .flatMapMany(Flux::fromIterable)
                .flatMap(chargingPoint -> new DataAccessResponseHandler<ChargingPointDataResponse>()
                        .handleResponse(Mono.just(chargingPoint), HttpStatus.OK)
                        .map(ResponseEntity::getBody))
                .collectList()
                .flatMap(list -> list.isEmpty()
                        ? Mono.just(ResponseEntity.notFound().build())
                        : Mono.just(ResponseEntity.ok(list)));
    }



    @GetMapping("/getChargingPoint/{cpId}")
    public Mono<ResponseEntity<ChargingPointDataResponse>> getChargingPoint(@PathVariable String cpId) {
        return CMSEndpointService.getChargingPoint(cpId)
                .flatMap(chargingPoint -> {
                    if (chargingPoint == null) {
                        return Mono.just(ResponseEntity.notFound().build());
                    } else {
                        return new DataAccessResponseHandler<ChargingPointDataResponse>()
                                .handleResponse(Mono.just(chargingPoint), HttpStatus.OK);
                    }
                });
    }


}




