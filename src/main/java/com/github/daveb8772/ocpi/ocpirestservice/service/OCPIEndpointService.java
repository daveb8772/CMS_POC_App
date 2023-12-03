package com.github.daveb8772.ocpi.ocpirestservice.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class OCPIEndpointService {

    // Method to retrieve supported OCPI versions
    public List<String> getSupportedVersions() {
        // Logic to retrieve supported OCPI versions from database or configuration
        return Arrays.asList("2.2", "2.1", "2.0");
    }

    // Other methods to handle OCPI functionalities...
}
