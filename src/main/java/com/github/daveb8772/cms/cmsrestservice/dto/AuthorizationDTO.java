package com.github.daveb8772.cms.cmsrestservice.dto;

import com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels.Authorization;

import java.time.ZonedDateTime;

public class AuthorizationDTO {

    private Long id;
    private Boolean isAuthorized;
    private String accessToken;
    private ZonedDateTime expiresAt;
    private String refreshToken;

    // Constructors
    public AuthorizationDTO() {}

    public AuthorizationDTO(Long id, Boolean isAuthorized, String accessToken, ZonedDateTime expiresAt, String refreshToken) {
        this.id = id;
        this.isAuthorized = isAuthorized;
        this.accessToken = accessToken;
        this.expiresAt = expiresAt;
        this.refreshToken = refreshToken;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsAuthorized() {
        return isAuthorized;
    }

    public void setIsAuthorized(Boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
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

    // Static method to convert entity to DTO
    public static AuthorizationDTO fromEntity(Authorization entity) {
        return new AuthorizationDTO(
                entity.getId(),
                entity.getIsAuthorized(),
                entity.getAccessToken(),
                entity.getExpiresAt(),
                entity.getRefreshToken()
        );
    }
}
