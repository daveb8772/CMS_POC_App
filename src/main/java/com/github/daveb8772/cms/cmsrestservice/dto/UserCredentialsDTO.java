package com.github.daveb8772.cms.cmsrestservice.dto;

public class UserCredentialsDTO {
    private String username;
    private String password;

    // Constructors, Getters, and Setters

    public UserCredentialsDTO() {
    }

    public UserCredentialsDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

