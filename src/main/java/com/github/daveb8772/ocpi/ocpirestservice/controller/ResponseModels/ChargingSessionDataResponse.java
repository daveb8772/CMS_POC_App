package com.github.daveb8772.ocpi.ocpirestservice.controller.ResponseModels;

import com.github.daveb8772.ocpi.ocpirestservice.controller.ChargeModels.ChargingSession;

import java.util.List;

public class ChargingSessionDataResponse implements StatusAwareResponse {

    private List<ChargingSession> chargingSessions;
    private ResponseStatus responseStatus;

    public ChargingSessionDataResponse(List<ChargingSession> chargingSessions) {
        this.chargingSessions = chargingSessions;
    }

    public List<ChargingSession> getChargingSessions() {
        return chargingSessions;
    }

    public void setChargingSessions(List<ChargingSession> chargingSessions) {
        this.chargingSessions = chargingSessions;
    }

    public ResponseStatus getStatus() {
        return responseStatus;
    }

    @Override
    public void setStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public boolean isEmpty() {
        return chargingSessions == null || chargingSessions.isEmpty();
    }
}
