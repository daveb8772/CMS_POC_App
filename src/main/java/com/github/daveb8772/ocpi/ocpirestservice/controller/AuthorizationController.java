package com.github.daveb8772.ocpi.ocpirestservice.controller;

import com.github.daveb8772.ocpi.ocpirestservice.controller.ResponseModels.AuthorizationResponse;
import com.github.daveb8772.ocpi.ocpirestservice.service.OCPIEndpointService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/ocpi") // Base mapping for OCPI endpoints
public class AuthorizationController {

    @Autowired
    private OCPIEndpointService ocpiEndpointService;

    @Autowired
    private DataAccessResponseHandler<AuthorizationResponse> authorizationResponseHandler;


    @GetMapping("/authorizeUser")
    public Mono<ResponseEntity<AuthorizationResponse>> authorizeUser() {
        return ocpiEndpointService.authorizeUser()
                .flatMap(authorizationResponse -> {
                    if (authorizationResponse.getAuthorized() == null || !authorizationResponse.getAuthorized()) {
                        // Assuming an unauthorized response should be treated as an error
                        return Mono.just(new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
                    } else {
                        DataAccessResponseHandler<AuthorizationResponse> responseHandler =
                                new DataAccessResponseHandler<>(Mono.just(authorizationResponse));
                        return responseHandler.handleResponse(HttpStatus.OK);
                    }
                });
    }



    // Other relevant OCPI endpoints and mappings...
}

