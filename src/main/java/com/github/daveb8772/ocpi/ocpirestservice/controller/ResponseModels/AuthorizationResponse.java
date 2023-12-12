package com.github.daveb8772.ocpi.ocpirestservice.controller.ResponseModels;

import java.time.ZonedDateTime;
import java.util.List;

public class AuthorizationResponse  implements StatusAwareResponse{

    private Boolean authorized;
    private String accessToken;
    private List<String> authorizedScopes;
    private ZonedDateTime expiresAt;
    private String refreshToken;
    private List<Error> errors;

    public AuthorizationResponse(Boolean authorized, String accessToken, ZonedDateTime expiresAt, String refreshToken, List<Error> errors) {
        this.authorized = authorized;
        this.accessToken = accessToken;
        this.expiresAt = expiresAt;
        this.refreshToken = refreshToken;
        this.errors = errors;
        // status will be set using setStatus method
    }
    @Override
    public void setStatus(ResponseStatus responseStatus) {
    }

    public Boolean getAuthorized() {
        return authorized;
    }

    public void setAuthorized(Boolean authorized) {
        this.authorized = authorized;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public List<String> getAuthorizedScopes() {
        return authorizedScopes;
    }

    public void setAuthorizedScopes(List<String> authorizedScopes) {
        this.authorizedScopes = authorizedScopes;
    }

    public ZonedDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(ZonedDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }
}
