package com.github.daveb8772.ocpi.ocpirestservice.controller.Models.DepotModels;

public class Accessibility {

    private String accessibilityId; // Unique identifier for the accessibility information
    private boolean isAccessible; // Indicates whether the location is accessible
    private String description; // Detailed description of accessibility features
    private String additionalInformation; // Additional accessibility information

    public String getAccessibilityId() {
        return accessibilityId;
    }

    public void setAccessibilityId(String accessibilityId) {
        this.accessibilityId = accessibilityId;
    }

    public boolean isAccessible() {
        return isAccessible;
    }

    public void setAccessible(boolean isAccessible) {
        this.isAccessible = isAccessible;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }
}
