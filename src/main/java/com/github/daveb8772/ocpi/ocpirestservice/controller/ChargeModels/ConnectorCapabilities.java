package com.github.daveb8772.ocpi.ocpirestservice.controller.ChargeModels;

import java.util.Set;

public class ConnectorCapabilities {

    private boolean bidirectionalCharging;
    private boolean dynamicPowerSharing;

    private double maximumPower;


    private ConnectorType plugType; // Use the PlugType enum


    private ChargingModeValue chargeMode; // Use the ChargingMode enum
    private CommunicationProtocolValue communicationProtocol; // Use the CommunicationProtocol enum

    private Set<String> additionalFeatures; // Additional capabilities not covered by other fields

    public enum ChargingModeValue {
        AC,
        DC,
        FAST_AC,
        FAST_DC
    }
    public enum CommunicationProtocolValue {
        OCPP1_6,
        OCPP2_0,
        NONE
        // Add any additional communication protocols as needed.
    }
    public ConnectorCapabilities() {
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

    public ConnectorType getPlugType() {
        return plugType;
    }

    public void setPlugType(ConnectorType plugType) {
        this.plugType = plugType;
    }

    public ChargingModeValue getChargeMode() {
        return chargeMode;
    }

    public void setChargeMode(ChargingModeValue chargeMode) {
        this.chargeMode = chargeMode;
    }

    public double getMaximumPower() {
        return maximumPower;
    }

    public void setMaximumPower(double maximumPower) {
        this.maximumPower = maximumPower;
    }

    public CommunicationProtocolValue getCommunicationProtocol() {
        return communicationProtocol;
    }

    public void setCommunicationProtocol(CommunicationProtocolValue communicationProtocol) {
        this.communicationProtocol = communicationProtocol;
    }

    public Set<String> getAdditionalFeatures() {
        return additionalFeatures;
    }

    public void setAdditionalFeatures(Set<String> additionalFeatures) {
        this.additionalFeatures = additionalFeatures;
    }
}
