package com.github.daveb8772.ocpi.ocpirestservice.controller.DepotModels;

public class PointOfInterest {

    private String id; // Unique identifier for the point of interest
    private String type; // Type of point of interest (e.g., restaurant, hotel)
    private String name; // Human-readable name for the point of interest
    private String description; // Detailed description of the point of interest
    private String address; // Structured representation of the point of interest's address
    private String location; // Geographic coordinates of the point of interest
    private String imageUrl; // URL of a representative image for the point of interest

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;

    }
}
