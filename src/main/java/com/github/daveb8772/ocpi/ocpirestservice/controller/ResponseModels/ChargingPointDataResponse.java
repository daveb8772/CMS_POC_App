package com.github.daveb8772.ocpi.ocpirestservice.controller.ResponseModels;

import com.github.daveb8772.ocpi.ocpirestservice.controller.ChargeModels.ChargingPoint;

import java.util.List;

public class ChargingPointDataResponse implements StatusAwareResponse{

    private List<ChargingPoint> chargingPoints;
    private Status status;

    public ChargingPointDataResponse() {
    }

    public List<ChargingPoint> getChargingPoints() {
        return chargingPoints;
    }

    public void setChargingPoints(List<ChargingPoint> chargingPoints) {
        this.chargingPoints = chargingPoints;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }
}
