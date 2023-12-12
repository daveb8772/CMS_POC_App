package com.github.daveb8772.ocpi.ocpirestservice.controller.Models.ResponseModels;

public class CommandResponse implements StatusAwareResponse {

    private ResponseStatus responseStatus;
    private String result;

    // Constructors, getters, and setters

    @Override
    public void setStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public ResponseStatus getStatus() {
        return responseStatus;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    // Additional methods and properties as needed
}
