
package com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels;

import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.util.*;

@Entity
@Table(name = "charging_points")
public class ChargingPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chargingPointId;

    @Column(name = "status")
    private String status;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "chargingPoint")
    private List<Connector> connectors;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "charging_point_tariff",
            joinColumns = @JoinColumn(name = "charging_point_id"),
            inverseJoinColumns = @JoinColumn(name = "tariff_id")
    )
    private List<Tariff> tariffs;

    @Column(name = "connector_type")
    private String connectorType;

    @Column(name = "connector_status")
    private String connectorStatus;

    @Column(name = "connector_power")
    private double connectorPower;

    @Column(name = "availability_status")
    private String availabilityStatus;

    @Column(name = "current_utilization")
    private double currentUtilization;

    @Column(name = "last_update")
    private ZonedDateTime lastUpdate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChargingProfile> chargingProfiles;


    @Column(name = "max_charging_power")
    private double maxChargingPower;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "connector_capabilities_id")
    private ConnectorCapabilities connectorCapabilities;

    @OneToMany(mappedBy = "chargingPoint", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CommandRequest> commandRequests;



    public ChargingPoint() {
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


    public List<Connector> getConnectors() {
        return connectors;
    }

    public void setConnectors(List<Connector> connectors) {
        this.connectors = connectors;
    }

    public List<Tariff> getTariffs() {
        return tariffs;
    }

    public void setTariffs(List<Tariff> tariffs) {
        this.tariffs = tariffs;
    }

    public List<ChargingProfile> getChargingProfiles() {
        return chargingProfiles;
    }

    public void setChargingProfiles(List<ChargingProfile> chargingProfiles) {
        this.chargingProfiles = chargingProfiles;
    }


    public void addChargingProfile(ChargingProfile chargingProfile) {
        if (this.chargingProfiles == null) {
            this.chargingProfiles = new ArrayList<>();
        }
        this.chargingProfiles.add(chargingProfile);
    }

    public double getMaxChargingPower() {
        return maxChargingPower;
    }

    public void setMaxChargingPower(double maxChargingPower) {
        this.maxChargingPower = maxChargingPower;
    }

    public ConnectorCapabilities getConnectorCapabilities() {
        return connectorCapabilities;
    }

    public void setConnectorCapabilities(ConnectorCapabilities connectorCapabilities) {
        this.connectorCapabilities = connectorCapabilities;
    }


    public List<CommandRequest> getCommandRequests() {
        return commandRequests;
    }

    public void setCommandRequests(List<CommandRequest> commandRequests) {
        this.commandRequests = commandRequests;
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
}
