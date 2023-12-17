package com.github.daveb8772.cms.cmsrestservice.dto;

import com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels.ChargingProfile;

import java.time.LocalDateTime;

public class ChargingProfileDTO {

    private String id;
    private String name;
    private double chargingPower;
    private LocalDateTime validFrom;
    private LocalDateTime validTo;


    public static ChargingProfileDTO fromEntity(ChargingProfile chargingProfile) {
        ChargingProfileDTO dto = new ChargingProfileDTO();
        dto.setId(chargingProfile.getId());
        dto.setName(chargingProfile.getName());
        dto.setChargingPower(chargingProfile.getMaxChargingPower());
        dto.setValidFrom(chargingProfile.getValidFrom());
        dto.setValidTo(chargingProfile.getValidTo());
        return dto;
    }

    // Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getChargingPower() {
        return chargingPower;
    }

    public void setChargingPower(double chargingPower) {
        this.chargingPower = chargingPower;
    }

    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDateTime validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDateTime getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDateTime validTo) {
        this.validTo = validTo;
    }
}
