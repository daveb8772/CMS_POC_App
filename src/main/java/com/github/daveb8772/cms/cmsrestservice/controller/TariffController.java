package com.github.daveb8772.cms.cmsrestservice.controller;

import com.github.daveb8772.cms.cmsrestservice.service.CMSEndpointService;
import com.github.daveb8772.cms.cmsrestservice.controller.Models.ResponseModels.TariffDataResponse;
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
public class TariffController {

    @Autowired
    private CMSEndpointService CMSEndpointService;

    @GetMapping("/getTariffs")
    public Mono<ResponseEntity<List<TariffDataResponse>>> getTariffs() {
        return CMSEndpointService.getTariffs()
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
        return CMSEndpointService.getTariff(tariffId)
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
