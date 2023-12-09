package com.github.daveb8772.ocpi.ocpirestservice.controller.ResponseModels;

import com.github.daveb8772.ocpi.ocpirestservice.controller.ChargeModels.ChargingSession;

import java.util.List;

public class ChargingSessionDataResponse implements StatusAwareResponse {

    private List<ChargingSession> chargingSessions;
    private Status status;

    public ChargingSessionDataResponse(List<ChargingSession> chargingSessions) {
        this.chargingSessions = chargingSessions;
    }

    public List<ChargingSession> getChargingSessions() {
        return chargingSessions;
    }

    public void setChargingSessions(List<ChargingSession> chargingSessions) {
        this.chargingSessions = chargingSessions;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isEmpty() {
        return chargingSessions == null || chargingSessions.isEmpty();
    }
}
