package com.github.daveb8772.ocpi.ocpirestservice.controller;

import com.github.daveb8772.ocpi.ocpirestservice.controller.ResponseModels.UserInfoResponse;
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
public class UserController {

    @Autowired
    private OCPIEndpointService ocpiEndpointService;

    @Autowired
    private DataAccessResponseHandler<UserInfoResponse> userResponseHandler;

    @GetMapping("/getUserInfo")
    public Mono<ResponseEntity<UserInfoResponse>> getUserInfo() {
        return ocpiEndpointService.getUserInfo()
                .flatMap(userInfoResponse -> {
                    if (userInfoResponse == null) {
                        return Mono.just(new ResponseEntity<>(HttpStatus.NO_CONTENT));
                    } else {
                        DataAccessResponseHandler<UserInfoResponse> responseHandler =
                                new DataAccessResponseHandler<>(Mono.just(userInfoResponse));
                        return responseHandler.handleResponse(HttpStatus.OK);
                    }
                });
    }


    // Other relevant OCPI endpoints and mappings...
}

