package com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels;

import jakarta.persistence.*;

import java.util.Set;
@Entity
@Table(name = "connector_capabilities")
public class ConnectorCapabilities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bidirectional_charging")
    private boolean bidirectionalCharging;

    @Column(name = "dynamic_power_sharing")
    private boolean dynamicPowerSharing;

    @Column(name = "maximum_power")
    private double maximumPower;

    @Enumerated(EnumType.STRING)
    @Column(name = "plug_type")
    private ConnectorType plugType;

    @Enumerated(EnumType.STRING)
    @Column(name = "charge_mode")
    private ChargingModeValue chargeMode;

    @Enumerated(EnumType.STRING)
    @Column(name = "communication_protocol")
    private CommunicationProtocolValue communicationProtocol;



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

    public enum ConnectorType {
        TYPE1,
        TYPE2,
        CCS_COMBO_2,
        CHAdeMO,
        TESLA
    }


    public ConnectorCapabilities() {
    }

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

}
