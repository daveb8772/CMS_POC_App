package com.github.daveb8772.ocpi.ocpirestservice.controller;

import com.github.daveb8772.ocpi.ocpirestservice.controller.Models.ResponseModels.SupportedVersionsResponse;
import com.github.daveb8772.ocpi.ocpirestservice.service.OCPIEndpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ocpi/versions") // Base mapping for OCPI version endpoints
public class OCPIVersionController {

    @Autowired
    private OCPIEndpointService ocpiEndpointService;

    @GetMapping
    public ResponseEntity<SupportedVersionsResponse> getSupportedVersions() {
        SupportedVersionsResponse versionsResponse = ocpiEndpointService.getSupportedVersions();
        return ResponseEntity.ok(versionsResponse);
    }

    // Additional OCPI endpoints can be added here...
}
