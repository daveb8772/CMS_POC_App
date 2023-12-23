package com.github.daveb8772.cms.cmsrestservice.dto;


import com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels.Connector;

public class ConnectorDTO {

    private Long connectorId;
    private String connectorType; // Assuming you convert Enum to String for the DTO
    private String chargingMode; // Assuming you convert Enum to String for the DTO
    private String status;
    private double power;
    private boolean isOccupied;
    private String currentChargingSessionId;

    private Long chargingPointId;

    public static ConnectorDTO fromEntity(Connector entity) {
        ConnectorDTO dto = new ConnectorDTO();
        dto.setconnectorId(entity.getConnectorId());
        dto.setConnectorType(entity.getConnectorType().name());
        dto.setChargingMode(entity.getChargingMode().name());
        dto.setStatus(entity.getStatus());
        dto.setPower(entity.getPower());
        dto.setOccupied(entity.isOccupied());
        dto.setCurrentChargingSessionId(entity.getCurrentChargingSessionId());

        if (entity.getChargingPoint() != null) {
            dto.setChargingPointId(entity.getChargingPoint().getChargingPointId());
        }

        return dto;
    }

    // Constructors, getters, and setters
    public Long getconnectorId() {
        return connectorId;
    }

    public void setconnectorId(Long connectorId) {
        this.connectorId = connectorId;
    }

    public String getConnectorType() {
        return connectorType;
    }

    public void setConnectorType(String connectorType) {
        this.connectorType = connectorType;
    }

    public String getChargingMode() {
        return chargingMode;
    }

    public void setChargingMode(String chargingMode) {
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

    public Long getChargingPointId() {
        return chargingPointId;
    }

    public void setChargingPointId(Long chargingPointId) {
        this.chargingPointId = chargingPointId;
    }


}
