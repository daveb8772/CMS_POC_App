package com.github.daveb8772.cms.cmsrestservice.controller;

import com.github.daveb8772.cms.cmsrestservice.controller.Models.ResponseModels.LocationInfoResponse;
import com.github.daveb8772.cms.cmsrestservice.service.CMSEndpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Ensure to import your correct Location class
import com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels.LocationInfo; // Replace with your actual Location class import
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/ocpi")
public class LocationController {

    @Autowired
    private CMSEndpointService CMSEndpointService;


    @GetMapping("/getLocationInfo")
    public Mono<ResponseEntity<LocationInfoResponse>> getLocationInfo() {
        return CMSEndpointService.getLocationInfo()
                .map(locationInfo -> ResponseEntity.ok(locationInfo))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    // Other endpoints...
}
