package com.github.daveb8772.ocpi.ocpirestservice.controller.ResponseModels;

import com.github.daveb8772.ocpi.ocpirestservice.controller.ChargeModels.Tariff;

import java.util.List;

public class TariffDataResponse implements StatusAwareResponse {

    private List<Tariff> tariffs;
    private ResponseStatus responseStatus;

    public TariffDataResponse() {
    }

    public List<Tariff> getTariffs() {
        return tariffs;
    }

    public void setTariffs(List<Tariff> tariffs) {
        this.tariffs = tariffs;
    }

    public ResponseStatus getStatus() {
        return responseStatus;
    }

    @Override
    public void setStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }
}
