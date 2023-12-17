package com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels;

import jakarta.persistence.*;

@Entity
@Table(name = "usercredentials")
public class UserCredentials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
    @OneToOne(mappedBy = "userCredentials", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Authorization authorization;

    // Constructors
    public UserCredentials() {
    }

    public UserCredentials(String name, String password) {
        this.name = name;
        this.password = password;
    }

    // Getters and Setters

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Authorization getAuthorization() {
        return authorization;
    }

    public void setAuthorization(Authorization authorization) {
        this.authorization = authorization;
    }
}

