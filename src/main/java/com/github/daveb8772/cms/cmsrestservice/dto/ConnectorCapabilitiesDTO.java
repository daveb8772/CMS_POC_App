
package com.github.daveb8772.cms.cmsrestservice.dto;

import com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels.ConnectorCapabilities;

import java.util.Set;

public class ConnectorCapabilitiesDTO {

    private Long id;
    private boolean bidirectionalCharging;
    private boolean dynamicPowerSharing;
    private double maximumPower;
    private String plugType;
    private String chargeMode;
    private String communicationProtocol;


    public static ConnectorCapabilitiesDTO fromEntity(ConnectorCapabilities entity) {
        ConnectorCapabilitiesDTO dto = new ConnectorCapabilitiesDTO();
        dto.setId(entity.getId());
        dto.setBidirectionalCharging(entity.isBidirectionalCharging());
        dto.setDynamicPowerSharing(entity.isDynamicPowerSharing());
        dto.setMaximumPower(entity.getMaximumPower());
        dto.setPlugType(entity.getPlugType().name());
        dto.setChargeMode(entity.getChargeMode().name());
        dto.setCommunicationProtocol(entity.getCommunicationProtocol().name());


        return dto;
    }
    // Constructors, getters, and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isBidirectionalCharging() {
        return bidirectionalCharging;
    }

    public void setBidirectionalCharging(boolean bidirectionalCharging) {
        this.bidirectionalCharging = bidirectionalCharging;
    }

    public boolean isDynamicPowerSharing() {
        return dynamicPowerSharing;
    }

    public void setDynamicPowerSharing(boolean dynamicPowerSharing) {
        this.dynamicPowerSharing = dynamicPowerSharing;
    }

    public double getMaximumPower() {
        return maximumPower;
    }

    public void setMaximumPower(double maximumPower) {
        this.maximumPower = maximumPower;
    }

    public String getPlugType() {
        return plugType;
    }

    public void setPlugType(String plugType) {
        this.plugType = plugType;
    }

    public String getChargeMode() {
        return chargeMode;
    }

    public void setChargeMode(String chargeMode) {
        this.chargeMode = chargeMode;
    }

    public String getCommunicationProtocol() {
        return communicationProtocol;
    }

    public void setCommunicationProtocol(String communicationProtocol) {
        this.communicationProtocol = communicationProtocol;
    }

}
