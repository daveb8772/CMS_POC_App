package com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "charging_profiles")
public class ChargingProfile {

    @Id
    @Column(name = "profile_id")
    private String id;

    @Column(name = "profile_name")
    private String name;

    @Column(name = "charging_power")
    private double chargingPower;

    @Column(name = "valid_from")
    private LocalDateTime validFrom;

    @Column(name = "valid_to")
    private LocalDateTime validTo;



    public ChargingProfile() {
    }

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

    public double getMaxChargingPower() {
        return chargingPower;
    }

    public void setMaxChargingPower(double chargingPower) {
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

