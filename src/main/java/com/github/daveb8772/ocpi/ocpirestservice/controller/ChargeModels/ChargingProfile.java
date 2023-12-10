package com.github.daveb8772.ocpi.ocpirestservice.controller.ChargeModels;

import jakarta.persistence.*;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "connector_type")
    private ConnectorCapabilities.ConnectorType connectorType;

    @Enumerated(EnumType.STRING)
    @Column(name = "charging_mode")
    private ConnectorCapabilities.ChargingModeValue chargingMode;


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

    public ConnectorCapabilities.ConnectorType getConnectorType() {
        return connectorType;
    }

    public void setConnectorType(ConnectorCapabilities.ConnectorType connectorType) {
        this.connectorType = connectorType;
    }

    public ConnectorCapabilities.ChargingModeValue getChargingMode() {
        return chargingMode;
    }

    public void setChargingMode(ConnectorCapabilities.ChargingModeValue chargingMode) {
        this.chargingMode = chargingMode;
    }
}

