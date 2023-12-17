package com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels;

import jakarta.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "authorizations")
public class Authorization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Boolean getIsAuthorized() {
        return isAuthorized;
    }

    public void setIsAuthorized(Boolean authorized) {
        isAuthorized = authorized;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Column(name = "is_authorized")
    private Boolean isAuthorized;

    @Column(name = "access_token")
    private String accessToken;

    public void setExpiresAt(ZonedDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    @Column(name = "expires_at")
    private ZonedDateTime expiresAt;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Column(name = "refresh_token")
    private String refreshToken;

    public Long getId() {
        return this.id;
    }

    public ZonedDateTime getExpiresAt() {
        return this.expiresAt;
    }

    // Constructors, getters, and setters
}
