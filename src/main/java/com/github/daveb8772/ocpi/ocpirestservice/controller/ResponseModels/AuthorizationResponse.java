package com.github.daveb8772.ocpi.ocpirestservice.controller.ResponseModels;

import com.github.daveb8772.ocpi.ocpirestservice.controller.ChargeModels.Authorization;

public class AuthorizationResponse implements StatusAwareResponse {

    public Authorization getAuthorization() {
        return authorization;
    }

    public void setAuthorization(Authorization authorization) {
        this.authorization = authorization;
    }

    private Authorization authorization;
    private ResponseStatus responseStatus;

    public AuthorizationResponse(Authorization authorization) {
        this.authorization = authorization;
    }

    // Getters and setters for authorization

    @Override
    public void setStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public ResponseStatus getStatus() {
        return responseStatus;
    }
}
