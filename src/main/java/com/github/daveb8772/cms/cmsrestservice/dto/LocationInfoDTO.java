package com.github.daveb8772.cms.cmsrestservice.dto;


import com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels.LocationInfo;

import java.util.List;
import java.util.stream.Collectors;

public class LocationInfoDTO {


    private String locationId;
    private AddressDTO locationAddress;
    private String name;
    private String details;
    private String openingHours;
    private ContactDetailsDTO contactInformation;
    private AccessibilityDTO accessibilityInformation;
    // Assuming PointOfInterestDTO is also defined somewhere
    private List<PointOfInterestDTO> pointsOfInterest;

    public static LocationInfoDTO fromEntity(LocationInfo entity) {
        LocationInfoDTO dto = new LocationInfoDTO();
        dto.setLocationId(entity.getLocation_id());
        dto.setLocationAddress(AddressDTO.fromEntity(entity.getAddress()));
        dto.setName(entity.getName());
        dto.setDetails(entity.getDetails());
        dto.setOpeningHours(entity.getOpeningHours());
        dto.setContactInformation(ContactDetailsDTO.fromEntity(entity.getContactInformation()));
        dto.setAccessibilityInformation(AccessibilityDTO.fromEntity(entity.getAccessibilityInformation()));
        dto.setPointsOfInterest(entity.getPointsOfInterest().stream()
                .map(PointOfInterestDTO::fromEntity)
                .collect(Collectors.toList()));

        return dto;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public AddressDTO getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(AddressDTO locationAddress) {
        this.locationAddress = locationAddress;
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

    public ContactDetailsDTO getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(ContactDetailsDTO contactInformation) {
        this.contactInformation = contactInformation;
    }

    public AccessibilityDTO getAccessibilityInformation() {
        return accessibilityInformation;
    }

    public void setAccessibilityInformation(AccessibilityDTO accessibilityInformation) {
        this.accessibilityInformation = accessibilityInformation;
    }

    public List<PointOfInterestDTO> getPointsOfInterest() {
        return pointsOfInterest;
    }

    public void setPointsOfInterest(List<PointOfInterestDTO> pointsOfInterest) {
        this.pointsOfInterest = pointsOfInterest;
    }

    // Constructors, getters, and setters
}

