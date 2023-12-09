package com.github.daveb8772.ocpi.ocpirestservice.controller;

import com.github.daveb8772.ocpi.ocpirestservice.service.OCPIEndpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// In OCPIController

@RestController
@RequestMapping("/ocpi")
public class OCPIController {

    @Autowired
    private OCPIEndpointService ocpiEndpointService;




    // Other OCPI endpoints and mappings...
}

