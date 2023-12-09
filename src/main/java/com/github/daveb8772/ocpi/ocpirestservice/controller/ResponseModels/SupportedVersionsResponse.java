package com.github.daveb8772.ocpi.ocpirestservice.controller.ResponseModels;

import java.util.List;

public class SupportedVersionsResponse implements StatusAwareResponse{

    private List<String> supportedVersions;


    @Override
    public void setStatus(Status status) {
    }
    public SupportedVersionsResponse(List<String> versions) {
        this.supportedVersions = versions;
    }


    public List<String> getSupportedVersions() {
        return supportedVersions;
    }

    public void setSupportedVersions(List<String> supportedVersions) {
        this.supportedVersions = supportedVersions;
    }

    @Override
    public String toString() {
        return "SupportedVersionsResponse{" +
                "supportedVersions=" + supportedVersions +
                '}';
    }
}
