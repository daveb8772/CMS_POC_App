package com.github.daveb8772.ocpi.ocpirestservice.controller.ChargeModels;

public class Dispenser {

    private String dispenserId; // Unique identifier for the dispenser
    private String connectorId; // Identifier for the connector to which the dispenser belongs
    private ConnectorType connectorType; // Type of connector (e.g., Type 1, Type 2, CCS Combo 2, CHAdeMO)
    private ConnectorCapabilities.ChargingModeValue chargingMode; // Current mode of the dispenser (e.g., AC, DC, FAST_AC, FAST_DC)
    private String status; // Current status of the dispenser (e.g., Available, Unavailable, Reserved, Fault)
    private double power; // Maximum charging power supported by the dispenser
    private boolean isOccupied; // Indicates whether the dispenser is currently occupied by a vehicle
    private String currentChargingSessionId; // ID of the current charging session, if any

    public Dispenser() {
    }

    public String getId() {
        return dispenserId;
    }

    public void setId(String dispenserId) {
        this.dispenserId = dispenserId;
    }

    public String getConnectorId() {
        return connectorId;
    }

    public void setConnectorId(String connectorId) {
        this.connectorId = connectorId;
    }

    public ConnectorType getConnectorType() {
        return connectorType;
    }

    public void setConnectorType(ConnectorType connectorType) {
        this.connectorType = connectorType;
    }

    public ConnectorCapabilities.ChargingModeValue getChargingMode() {
        return chargingMode;
    }

    public void setChargingMode(ConnectorCapabilities.ChargingModeValue chargingMode) {
        this.chargingMode = chargingMode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public String getCurrentChargingSessionId() {
        return currentChargingSessionId;
    }

    public void setCurrentChargingSessionId(String currentChargingSessionId) {
        this.currentChargingSessionId = currentChargingSessionId;
    }
}

