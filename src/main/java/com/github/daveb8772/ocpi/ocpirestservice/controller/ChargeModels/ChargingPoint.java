
package com.github.daveb8772.ocpi.ocpirestservice.controller.ChargeModels;

import com.github.daveb8772.ocpi.ocpirestservice.controller.DepotModels.LocationInfo;

import java.time.ZonedDateTime;
import java.util.*;

public class ChargingPoint {

    private String chargingPointId;
    private String rfId;
    private String status;
    private LocationInfo location;
    private Set<Dispenser> dispensers; // Represents the outlets associated with the charging point
    private String connectorType;
    private String connectorStatus;
    private double connectorPower; // Maximum charging power supported by the connector
    private String availabilityStatus;
    private double currentUtilization; // Current power consumption of the charging point
    private ZonedDateTime lastUpdate; // Represents the last time the charging point information was updated
    private List<ChargingProfile> chargingProfiles;
    private Map<String, Tariff> tariffs; // Map of tariff IDs to corresponding `Tariff` objects
    private double maxChargingPower; // Added property for maximum charging power
    private ConnectorCapabilities connectorCapabilities; // Added property for connector capabilities

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

    public Set<Dispenser> getDispensers() {
        return dispensers;
    }

    public void setDispensers(Set<Dispenser> dispensers) {
        this.dispensers = dispensers;
    }

    public List<ChargingProfile> getChargingProfiles() {
        return chargingProfiles;
    }

    public void setChargingProfiles(List<ChargingProfile> chargingProfiles) {
        this.chargingProfiles = chargingProfiles;
    }

    public Map<String, Tariff> getTariffs() {
        return tariffs;
    }

    public void setTariffs(Map<String, Tariff> tariffs) {
        this.tariffs = tariffs;
    }

    public void addChargingProfile(ChargingProfile chargingProfile) {
        if (this.chargingProfiles == null) {
            this.chargingProfiles = new ArrayList<>();
        }
        this.chargingProfiles.add(chargingProfile);
    }

    public void addTariff(String tariffId, Tariff tariff) {
        if (this.tariffs == null) {
            this.tariffs = new HashMap<>();
        }
        this.tariffs.put(tariffId, tariff);
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
