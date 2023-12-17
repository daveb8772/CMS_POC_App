
package com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels;

import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.util.*;

@Entity
@Table(name = "charging_points")
public class ChargingPoint {

    @Id
    @Column(name = "charging_point_id")
    private String chargingPointId;

    @Column(name = "rf_id")
    private String rfId;

    @Column(name = "status")
    private String status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_info_id")
    private LocationInfo location;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "chargingPoint")
    private Set<Connector> connectors;

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



    // JPA does not support Map field types directly.
    //private Map<String, Tariff> tariffs; // Map of tariff IDs to corresponding `Tariff` objects
    // So extracted the Tariff info and left the ID - Tariff IDs are stored as strings
    @ElementCollection
    @CollectionTable(name = "charging_point_tariffs",
            joinColumns = @JoinColumn(name = "charging_point_id"))
    @Column(name = "tariff_id")
    private List<String> tariffIds;

    @Column(name = "max_charging_power")
    private double maxChargingPower;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "connector_capabilities_id", referencedColumnName = "connector_id")
    private ConnectorCapabilities connectorCapabilities;

    @OneToMany(mappedBy = "chargingPoint", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CommandRequest> commandRequests;



    public ChargingPoint() {
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

    public LocationInfo getLocation() {
        return location;
    }

    public void setLocation(LocationInfo location) {
        this.location = location;
    }

    public Set<Connector> getConnectors() {
        return connectors;
    }

    public void setConnectors(Set<Connector> connectors) {
        this.connectors = connectors;
    }

    public List<ChargingProfile> getChargingProfiles() {
        return chargingProfiles;
    }

    public void setChargingProfiles(List<ChargingProfile> chargingProfiles) {
        this.chargingProfiles = chargingProfiles;
    }
    public List<String> getTariffIds() {
        return tariffIds;
    }

    public void setTariffIds(List<String> tariffIds) {
        this.tariffIds = tariffIds;
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
