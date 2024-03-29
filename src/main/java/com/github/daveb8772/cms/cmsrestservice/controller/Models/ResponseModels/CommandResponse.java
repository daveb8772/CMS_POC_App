package com.github.daveb8772.cms.cmsrestservice.controller.Models.ResponseModels;

import com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels.CommandRequest;

public class CommandResponse implements StatusAwareResponse {

    private CommandRequest commandRequest;
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

    public CommandRequest getCommandRequest() {
        return commandRequest;
    }

    public void setCommandRequest(CommandRequest commandRequest) {
        this.commandRequest = commandRequest;
    }

    // Additional methods and properties as needed
}
