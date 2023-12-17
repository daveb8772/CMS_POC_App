package com.github.daveb8772.cms.cmsrestservice.controller.Models.ResponseModels;

import com.github.daveb8772.cms.cmsrestservice.dto.TariffDTO; // Import TariffDTO

import java.util.List;

public class TariffDataResponse implements StatusAwareResponse {

    private List<TariffDTO> tariffs; // Change to use TariffDTO
    private ResponseStatus responseStatus;

    public TariffDataResponse() {
    }

    public List<TariffDTO> getTariffs() {
        return tariffs;
    }

    public void setTariffs(List<TariffDTO> tariffs) {
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
