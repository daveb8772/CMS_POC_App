package com.github.daveb8772.cms.cmsrestservice.dto;


import com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels.ChargingPoint;
import com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels.Tariff;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ChargingPointDTO {

    private Long chargingPointId;
    private String status;
    private Set<ConnectorDTO> connector;
    private String connectorType;
    private String connectorStatus;
    private double connectorPower;
    private String availabilityStatus;
    private double currentUtilization;
    private ZonedDateTime lastUpdate;
    private List<Tariff> tariffs;
    private double maxChargingPower;
    private ConnectorCapabilitiesDTO connectorCapabilities;
    private List<ChargingProfileDTO> chargingProfiles;
    private List<CommandRequestDTO> commandRequests;



    public static ChargingPointDTO fromEntity(ChargingPoint chargingPoint) {
        ChargingPointDTO dto = new ChargingPointDTO();
        dto.setChargingPointId(chargingPoint.getChargingPointId());
        dto.setStatus(chargingPoint.getStatus());
        dto.setConnectorType(chargingPoint.getConnectorType());
        dto.setConnectorStatus(chargingPoint.getConnectorStatus());
        dto.setConnectorPower(chargingPoint.getConnectorPower());
        dto.setAvailabilityStatus(chargingPoint.getAvailabilityStatus());
        dto.setCurrentUtilization(chargingPoint.getCurrentUtilization());
        dto.setLastUpdate(chargingPoint.getLastUpdate());
        dto.setMaxChargingPower(chargingPoint.getMaxChargingPower());
        dto.setTariffs(new ArrayList<>(chargingPoint.getTariffs()));
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

    private void setTariffs(ArrayList<Tariff> tariffs) {
        this.tariffs = tariffs;
    }

    public Long getChargingPointId() {
        return chargingPointId;
    }

    public void setChargingPointId(Long chargingPointId) {
        this.chargingPointId = chargingPointId;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public List<Tariff> getTariffs() {
        return tariffs;
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