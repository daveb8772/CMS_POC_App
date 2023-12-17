package com.github.daveb8772.cms.cmsrestservice.controller.Models.ResponseModels;

import com.github.daveb8772.cms.cmsrestservice.dto.AuthorizationDTO;

public class AuthorizationResponse implements StatusAwareResponse {

    private AuthorizationDTO authorization;
    private ResponseStatus responseStatus;

    public AuthorizationResponse() {
        // No-args constructor
    }

    public AuthorizationResponse(AuthorizationDTO authorization) {
        this.authorization = authorization;
    }

    // Getters and setters for authorizationDTO

    public AuthorizationDTO getAuthorization() {
        return authorization;
    }

    public void setAuthorization(AuthorizationDTO authorization) {
        this.authorization = authorization;
    }

    @Override
    public void setStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public ResponseStatus getStatus() {
        return responseStatus;
    }
}
