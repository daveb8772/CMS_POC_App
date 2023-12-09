package com.github.daveb8772.ocpi.ocpirestservice.controller.ResponseModels;

public class UserInfoResponse implements StatusAwareResponse {

    public UserInfoResponse(String userId, String fullName, String emailAddress, String phoneNumber, String languagePreference) {
    }

    // Getters and setters for all fields

    @Override
    public void setStatus(Status status) {
    }
}
