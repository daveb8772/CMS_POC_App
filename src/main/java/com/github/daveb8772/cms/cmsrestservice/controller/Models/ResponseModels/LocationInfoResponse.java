package com.github.daveb8772.cms.cmsrestservice.controller.Models.ResponseModels;

import com.github.daveb8772.cms.cmsrestservice.dto.LocationInfoDTO;

public class LocationInfoResponse {
    private LocationInfoDTO locationInfo;
    private ResponseStatus responseStatus;

    // Constructors, getters, and setters
    public LocationInfoResponse(LocationInfoDTO locationInfo) {
        this.locationInfo = locationInfo;
    }

    public LocationInfoResponse(LocationInfoDTO locationInfo, ResponseStatus responseStatus) {
        this.locationInfo = locationInfo;
        this.responseStatus = responseStatus;
    }


    public LocationInfoDTO getLocationInfo() {
        return locationInfo;
    }

    public void setLocationInfo(LocationInfoDTO locationInfo) {
        this.locationInfo = locationInfo;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }
}
