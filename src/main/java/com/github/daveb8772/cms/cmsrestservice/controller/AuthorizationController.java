package com.github.daveb8772.cms.cmsrestservice.controller;

import com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels.UserCredentials;
import com.github.daveb8772.cms.cmsrestservice.controller.Models.ResponseModels.AuthorizationResponse;
import com.github.daveb8772.cms.cmsrestservice.controller.Models.ResponseModels.ResponseStatus;
import com.github.daveb8772.cms.cmsrestservice.service.CMSEndpointService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/cms") // Base mapping for cms endpoints
public class AuthorizationController {

    @Autowired
    private CMSEndpointService CMSEndpointService;

    @Autowired
    private DataAccessResponseHandler<AuthorizationResponse> authorizationResponseHandler;

    @PostMapping("/user/auth")
    public Mono<ResponseEntity<?>> authenticateUser(@RequestBody UserCredentials credentials) {
        return CMSEndpointService.authorizeUser(credentials)
                .flatMap(authorizationResponse -> {
                    if (authorizationResponse != null && authorizationResponse.getAuthorization().getIsAuthorized()) {
                        // User is authorized
                        ResponseStatus responseStatus = new ResponseStatus(HttpStatus.OK.value(), "Authentication successful.");
                        return authorizationResponseHandler.handleResponse(Mono.just(authorizationResponse), HttpStatus.OK);
                    } else {
                        // Authentication failed
                        ResponseStatus responseStatus = new ResponseStatus(HttpStatus.UNAUTHORIZED.value(), "Authentication failed. Invalid credentials.");
                        return Mono.just(new ResponseEntity<>(responseStatus, HttpStatus.UNAUTHORIZED));
                    }
                })
                .onErrorResume(error -> {
                    // Handle other errors
                    ResponseStatus responseStatus = new ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error.");
                    return Mono.just(new ResponseEntity<>(responseStatus, HttpStatus.INTERNAL_SERVER_ERROR));
                });
    }



}