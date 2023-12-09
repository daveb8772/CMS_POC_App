package com.github.daveb8772.ocpi.ocpirestservice.controller.ChargeModels;

public class ChargingProfile {

    private String id; // Unique identifier for the charging profile
    private String name; // Human-readable name for the charging profile
    private double chargingPower; // Maximum charging power supported by the profile
    private ConnectorType.ConnectorTypeValue connectorType; // Specifies the type of connector supported by the profile
    private ConnectorCapabilities.ChargingModeValue chargingMode; // Indicates the type of charging mode


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

    public ConnectorType.ConnectorTypeValue getConnectorType() {
        return connectorType;
    }

    public void setConnectorType(ConnectorType.ConnectorTypeValue connectorType) {
        this.connectorType = connectorType;
    }

    public ConnectorCapabilities.ChargingModeValue getChargingMode() {
        return chargingMode;
    }

    public void setChargingMode(ConnectorCapabilities.ChargingModeValue chargingMode) {
        this.chargingMode = chargingMode;
    }
}

