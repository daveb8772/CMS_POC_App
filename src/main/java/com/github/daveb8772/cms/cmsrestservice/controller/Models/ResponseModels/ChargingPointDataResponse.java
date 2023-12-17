package com.github.daveb8772.cms.cmsrestservice.controller.Models.ResponseModels;

import com.github.daveb8772.cms.cmsrestservice.dto.ChargingPointDTO;

import java.util.List;

public class ChargingPointDataResponse implements StatusAwareResponse{

    private List<ChargingPointDTO> chargingPoints;
    private ResponseStatus responseStatus;


    public ChargingPointDataResponse() {
    }

    public List<ChargingPointDTO> getChargingPoints() {
        return chargingPoints;
    }

    public void setChargingPoints(List<ChargingPointDTO> chargingPoints) {
        this.chargingPoints = chargingPoints;
    }

    public ResponseStatus getStatus() {
        return responseStatus;
    }

    @Override
    public void setStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }
}
