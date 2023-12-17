package com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels;

import jakarta.persistence.*;

@Entity
@Table(name = "connectors")
public class Connector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long connectorId;

    @Enumerated(EnumType.STRING)
    @Column(name = "connector_type")
    private ConnectorCapabilities.ConnectorType connectorType;

    @Enumerated(EnumType.STRING)
    @Column(name = "charging_mode")
    private ConnectorCapabilities.ChargingModeValue chargingMode;

    @Column(name = "status")
    private String status;

    @Column(name = "power")
    private double power;

    @Column(name = "is_occupied")
    private boolean isOccupied;

    @Column(name = "current_charging_session_id")
    private String currentChargingSessionId;

    public ChargingPoint getChargingPoint() {
        return chargingPoint;
    }

    public void setChargingPoint(ChargingPoint chargingPoint) {
        this.chargingPoint = chargingPoint;
    }

    @ManyToOne
    @JoinColumn(name = "charging_point_id") // Adjust the column name as per your schema
    private ChargingPoint chargingPoint;
    public Connector() {
    }

    public Long getId() {
        return connectorId;
    }

    public void setId(Long connectorId) {
        this.connectorId = connectorId;
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

