package com.github.daveb8772.cms.cmsrestservice.controller.Models.ResponseModels;

import com.github.daveb8772.cms.cmsrestservice.dto.ChargingSessionDTO;
import java.util.List;

public class ChargingSessionDataResponse implements StatusAwareResponse {

    private List<ChargingSessionDTO> chargingSessions;
    private ResponseStatus responseStatus;

    public ChargingSessionDataResponse(List<ChargingSessionDTO> chargingSessions) {
        this.chargingSessions = chargingSessions;
    }

    public List<ChargingSessionDTO> getChargingSessions() {
        return chargingSessions;
    }

    public void setChargingSessions(List<ChargingSessionDTO> chargingSessions) {
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
