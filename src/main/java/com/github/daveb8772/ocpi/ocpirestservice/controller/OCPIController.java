package com.github.daveb8772.ocpi.ocpirestservice.controller;

import com.github.daveb8772.ocpi.ocpirestservice.service.OCPIEndpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ocpi") // Base mapping for OCPI endpoints
public class
OCPIController {

    @Autowired
    private
    OCPIEndpointService ocpiEndpointService;

    @GetMapping("/versions") // Example OCPI endpoint for versions
    public ResponseEntity<List<String>> getOCPIVersions() {
        List<String> versions = ocpiEndpointService.getSupportedVersions();
        return ResponseEntity.ok(versions);
    }

    // Other OCPI endpoints and mappings...
}
