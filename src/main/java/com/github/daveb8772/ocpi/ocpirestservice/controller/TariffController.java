package com.github.daveb8772.ocpi.ocpirestservice.controller;

import com.github.daveb8772.ocpi.ocpirestservice.controller.DataAccessResponseHandler;
import com.github.daveb8772.ocpi.ocpirestservice.controller.ResponseModels.TariffDataResponse;
import com.github.daveb8772.ocpi.ocpirestservice.service.OCPIEndpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class TariffController {

    @Autowired
    private OCPIEndpointService ocpiEndpointService;

    @GetMapping("/getTariffs")
    public Mono<ResponseEntity<List<TariffDataResponse>>> getTariffs() {
        return ocpiEndpointService.getTariffs()
                .flatMapMany(Flux::fromIterable)
                .flatMap(tariff -> new DataAccessResponseHandler<TariffDataResponse>()
                        .handleResponse(Mono.just(tariff), HttpStatus.OK)
                        .map(ResponseEntity::getBody))
                .collectList()
                .flatMap(list -> list.isEmpty()
                        ? Mono.just(ResponseEntity.notFound().build())
                        : Mono.just(ResponseEntity.ok(list)));
    }

    @GetMapping("/getTariff/{tariffId}")
    public Mono<ResponseEntity<TariffDataResponse>> getTariff(@PathVariable String tariffId) {
        return ocpiEndpointService.getTariff(tariffId)
                .flatMap(tariff -> {
                    if (tariff == null) {
                        return Mono.just(ResponseEntity.notFound().build());
                    } else {
                        return new DataAccessResponseHandler<TariffDataResponse>()
                                .handleResponse(Mono.just(tariff), HttpStatus.OK);
                    }
                });
    }


    // You can add more endpoints here for creating, updating, and deleting tariffs.
}
