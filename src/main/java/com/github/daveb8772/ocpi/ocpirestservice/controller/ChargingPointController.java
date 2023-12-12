package com.github.daveb8772.ocpi.ocpirestservice.controller;

import com.github.daveb8772.ocpi.ocpirestservice.controller.Models.ResponseModels.ChargingPointDataResponse;
import com.github.daveb8772.ocpi.ocpirestservice.service.OCPIEndpointService;
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
@RequestMapping("/ocpi")
public class ChargingPointController {

    @Autowired
    private OCPIEndpointService ocpiEndpointService;


    @GetMapping("/getChargingPoints")
    public Mono<ResponseEntity<List<ChargingPointDataResponse>>> getChargingPoints() {
        return ocpiEndpointService.getChargingPoints()
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
        return ocpiEndpointService.getChargingPoint(cpId)
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




