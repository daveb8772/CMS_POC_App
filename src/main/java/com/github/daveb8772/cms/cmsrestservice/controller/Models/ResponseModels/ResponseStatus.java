package com.github.daveb8772.cms.cmsrestservice.controller.Models.ResponseModels;

import org.springframework.http.HttpStatus;

public class ResponseStatus {


    private String description;
    private int statusCode;

    // Default constructor
    public ResponseStatus() {
        // Default values can be set here if needed
    }


    public ResponseStatus(int statusCode, String description) {
        this.statusCode = statusCode;
        this.description = description;
    }

    public static ResponseStatus createStatus(HttpStatus httpStatus) {
        if (httpStatus == HttpStatus.OK) {
            return new ResponseStatus(httpStatus.value(), "Success");
        } else if (httpStatus == HttpStatus.BAD_REQUEST) {
            return new ResponseStatus(httpStatus.value(), "Invalid request");
        } else if (httpStatus == HttpStatus.NOT_FOUND) {
            return new ResponseStatus(httpStatus.value(), "Charging point not found");
        } else {
            return new ResponseStatus(httpStatus.value(), "Unexpected error");
        }
    }

    public String getDescription() {
        return description;
    }

    public void setMessage(String description) {
        this.description = description;
    }

    public int getStatus() {
        return statusCode;
    }

    public void setCode(int statusCode) {
        this.statusCode = statusCode;
    }
    // Getters and setters for statusCode and description
}