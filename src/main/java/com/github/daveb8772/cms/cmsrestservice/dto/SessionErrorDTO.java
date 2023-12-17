package com.github.daveb8772.cms.cmsrestservice.dto;

import com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels.SessionError;

public class SessionErrorDTO {

    private Long id;
    private String errorCode;
    private String errorMessage;

    public static SessionErrorDTO fromEntity(SessionError sessionEntity) {
        SessionErrorDTO dto = new SessionErrorDTO();

        dto.setId(sessionEntity.getId());
        dto.setErrorCode(sessionEntity.getErrorCode());
        dto.setErrorMessage(sessionEntity.getErrorMessage());

        return dto;
    }
    // Constructors, getters, and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
