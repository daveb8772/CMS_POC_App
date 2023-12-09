package com.github.daveb8772.ocpi.ocpirestservice.controller.DepotModels;

public class Accessibility {

    private String id; // Unique identifier for the accessibility information
    private boolean isAccessible; // Indicates whether the location is accessible
    private String description; // Detailed description of accessibility features
    private String additionalInformation; // Additional accessibility information

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
