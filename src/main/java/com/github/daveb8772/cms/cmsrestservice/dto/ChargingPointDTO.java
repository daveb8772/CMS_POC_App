package com.github.daveb8772.cms.cmsrestservice.dto;


import com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels.ChargingPoint;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ChargingPointDTO {

    private String chargingPointId;
    private String rfId;
    private String status;
    private LocationInfoDTO location;
    private Set<ConnectorDTO> connector;
    private String connectorType;
    private String connectorStatus;
    private double connectorPower;
    private String availabilityStatus;
    private double currentUtilization;
    private ZonedDateTime lastUpdate;
    private List<String> tariffIds;
    private double maxChargingPower;
    private ConnectorCapabilitiesDTO connectorCapabilities;
    private List<ChargingProfileDTO> chargingProfiles;
    private List<CommandRequestDTO> commandRequests;



    public static ChargingPointDTO fromEntity(ChargingPoint chargingPoint) {
        ChargingPointDTO dto = new ChargingPointDTO();
        dto.setChargingPointId(chargingPoint.getChargingPointId());
        dto.setRfId(chargingPoint.getRfId());
        dto.setStatus(chargingPoint.getStatus());
        dto.setConnectorType(chargingPoint.getConnectorType());
        dto.setConnectorStatus(chargingPoint.getConnectorStatus());
        dto.setConnectorPower(chargingPoint.getConnectorPower());
        dto.setAvailabilityStatus(chargingPoint.getAvailabilityStatus());
        dto.setCurrentUtilization(chargingPoint.getCurrentUtilization());
        dto.setLastUpdate(chargingPoint.getLastUpdate());
        dto.setMaxChargingPower(chargingPoint.getMaxChargingPower());
        dto.setTariffIds(new ArrayList<>(chargingPoint.getTariffIds()));
        dto.setLocation(LocationInfoDTO.fromEntity(chargingPoint.getLocation()));
        dto.setConnector(chargingPoint.getConnectors().stream()
                .map(ConnectorDTO::fromEntity)
                .collect(Collectors.toSet()));
        dto.setChargingProfiles(chargingPoint.getChargingProfiles().stream()
                .map(ChargingProfileDTO::fromEntity)
                .collect(Collectors.toList()));
        dto.setConnectorCapabilities(ConnectorCapabilitiesDTO.fromEntity(chargingPoint.getConnectorCapabilities()));
        dto.setCommandRequests(chargingPoint.getCommandRequests().stream()
                .map(CommandRequestDTO::fromEntity)
                .collect(Collectors.toList()));
        // Additional fields and conversion logic as needed

        return dto;
    }

    public String getChargingPointId() {
        return chargingPointId;
    }

    public void setChargingPointId(String chargingPointId) {
        this.chargingPointId = chargingPointId;
    }

    public String getRfId() {
        return rfId;
    }

    public void setRfId(String rfId) {
        this.rfId = rfId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocationInfoDTO getLocation() {
        return location;
    }

    public void setLocation(LocationInfoDTO location) {
        this.location = location;
    }

    public Set<ConnectorDTO> getConnector() {
        return connector;
    }

    public void setConnector(Set<ConnectorDTO> connector) {
        this.connector = connector;
    }

    public String getConnectorType() {
        return connectorType;
    }

    public void setConnectorType(String connectorType) {
        this.connectorType = connectorType;
    }

    public String getConnectorStatus() {
        return connectorStatus;
    }

    public void setConnectorStatus(String connectorStatus) {
        this.connectorStatus = connectorStatus;
    }

    public double getConnectorPower() {
        return connectorPower;
    }

    public void setConnectorPower(double connectorPower) {
        this.connectorPower = connectorPower;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public double getCurrentUtilization() {
        return currentUtilization;
    }

    public void setCurrentUtilization(double currentUtilization) {
        this.currentUtilization = currentUtilization;
    }

    public ZonedDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(ZonedDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public List<String> getTariffIds() {
        return tariffIds;
    }

    public void setTariffIds(List<String> tariffIds) {
        this.tariffIds = tariffIds;
    }

    public double getMaxChargingPower() {
        return maxChargingPower;
    }

    public void setMaxChargingPower(double maxChargingPower) {
        this.maxChargingPower = maxChargingPower;
    }

    public ConnectorCapabilitiesDTO getConnectorCapabilities() {
        return connectorCapabilities;
    }

    public void setConnectorCapabilities(ConnectorCapabilitiesDTO connectorCapabilities) {
        this.connectorCapabilities = connectorCapabilities;
    }

    public List<ChargingProfileDTO> getChargingProfiles() {
        return chargingProfiles;
    }

    public void setChargingProfiles(List<ChargingProfileDTO> chargingProfiles) {
        this.chargingProfiles = chargingProfiles;
    }

    public List<CommandRequestDTO> getCommandRequests() {
        return commandRequests;
    }

    public void setCommandRequests(List<CommandRequestDTO> commandRequests) {
        this.commandRequests = commandRequests;
    }



}