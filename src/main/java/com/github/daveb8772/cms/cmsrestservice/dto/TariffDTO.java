package com.github.daveb8772.cms.cmsrestservice.dto;

import com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels.Tariff;

public class TariffDTO {

    private Long tariffId;
    private String tariffName;
    private double price;

    // Constructors, getters, and setters

    public TariffDTO() {}

    public TariffDTO(Long tariffId, String tariffName, double price) {
        this.tariffId = tariffId;
        this.tariffName = tariffName;
        this.price = price;
    }

    // Getters and Setters

    public Long getTariffId() {
        return tariffId;
    }

    public void setTariffId(Long tariffId) {
        this.tariffId = tariffId;
    }

    public String getTariffName() {
        return tariffName;
    }

    public void setTariffName(String tariffName) {
        this.tariffName = tariffName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Static method to convert entity to DTO
    public static TariffDTO fromEntity(Tariff entity) {
        return new TariffDTO(
                entity.getTariffId(),
                entity.getTariffName(),
                entity.getPrice()
        );
    }
}
