package com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels;


import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "location_info")
public class LocationInfo {

    @Id
    @Column(name = "location_id")
    private String locationId;

    @Embedded
    private Address locationAddress;

    @Column(name = "name")
    private String name;

    @Column(name = "details")
    private String details;

    @Column(name = "opening_hours")
    private String openingHours;

    @Embedded
    private ContactDetails contactInformation;

    @Embedded
    private Accessibility accessibilityInformation;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "poi_id")
    private List<PointOfInterest> pointsOfInterest;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChargingPoint> chargingPoints; // Use Set for unique collection of charging points


    public String getLocation_id() {
        return locationId;
    }

    public void setLocation_id(String location_id) {
        this.locationId = location_id;
    }

    public Address getAddress() {
        return locationAddress;
    }

    public void setAddress(Address location_address) {
        this.locationAddress = location_address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public ContactDetails getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(ContactDetails contactInformation) {
        this.contactInformation = contactInformation;
    }

    public Accessibility getAccessibilityInformation() {
        return accessibilityInformation;
    }

    public void setAccessibilityInformation(Accessibility accessibilityInformation) {
        this.accessibilityInformation = accessibilityInformation;
    }

    public List<PointOfInterest> getPointsOfInterest() {
        return pointsOfInterest;
    }

    public void setPointsOfInterest(List<PointOfInterest> pointsOfInterest) {
        this.pointsOfInterest = pointsOfInterest;
    }

    public List<ChargingPoint> getChargingPoints() {
        return chargingPoints;
    }

    public void setChargingPoints(List<ChargingPoint> chargingPoints) {
        this.chargingPoints = chargingPoints;
    }
}

