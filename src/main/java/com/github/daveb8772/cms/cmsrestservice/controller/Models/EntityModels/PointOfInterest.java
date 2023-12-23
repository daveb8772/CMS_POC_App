package com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels;

import jakarta.persistence.*;

@Entity
@Table(name = "point_of_interest")
public class PointOfInterest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long poi_id; // Unique identifier for the point of interest

    @Column(name = "type")
    private String type;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "poi_address")
    private String poi_address;


    @ManyToOne
    @JoinColumn(name = "location_info_id") // This column in PointOfInterest table will reference LocationInfo's primary key
    private LocationInfo locationInfo;

    public LocationInfo getLocationInfo() {
        return locationInfo;
    }

    public void setLocationInfo(LocationInfo locationInfo) {
        this.locationInfo = locationInfo;
    }




    public Long getPoi_id() {
        return poi_id;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPoi_address() {
        return poi_address;
    }

    public void setPoi_address(String poi_address) {
        this.poi_address = poi_address;
    }
}
