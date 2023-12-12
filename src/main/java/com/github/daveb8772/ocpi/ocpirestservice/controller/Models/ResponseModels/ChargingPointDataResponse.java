package com.github.daveb8772.ocpi.ocpirestservice.controller.Models.ResponseModels;

import com.github.daveb8772.ocpi.ocpirestservice.controller.Models.DepotModels.ChargingPoint;

import java.util.List;

public class ChargingPointDataResponse implements StatusAwareResponse{

    private List<ChargingPoint> chargingPoints;
    private ResponseStatus responseStatus;

    public ChargingPointDataResponse() {
    }

    public List<ChargingPoint> getChargingPoints() {
        return chargingPoints;
    }

    public void setChargingPoints(List<ChargingPoint> chargingPoints) {
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
