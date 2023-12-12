package com.github.daveb8772.ocpi.ocpirestservice.controller;

import com.github.daveb8772.ocpi.ocpirestservice.service.OCPIEndpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Ensure to import your correct Location class
import com.github.daveb8772.ocpi.ocpirestservice.controller.Models.DepotModels.LocationInfo; // Replace with your actual Location class import
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/ocpi")
public class LocationController {

    @Autowired
    private OCPIEndpointService ocpiEndpointService;


    @GetMapping("/getLocationInfo")
    public Mono<ResponseEntity<LocationInfo>> getLocationInfo() {
        return ocpiEndpointService.getLocationInfo()
                .map(locationInfo -> ResponseEntity.ok(locationInfo))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    // Other endpoints...
}
