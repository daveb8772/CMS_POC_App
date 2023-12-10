package com.github.daveb8772.ocpi.ocpirestservice.controller.DepotModels;

import jakarta.persistence.*;

@Entity
@Table(name = "point_of_interest")
public class PointOfInterest {

    @Id
    @Column(name = "poi_id")
    private String poi_id; // Unique identifier for the point of interest

    @Column(name = "type")
    private String type;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "poi_address")
    private String poi_address;


    public String getId() {
        return poi_id;
    }

    public void setId(String poi_id) {
        this.poi_id = poi_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;

    }
}
