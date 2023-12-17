
package com.github.daveb8772.cms.cmsrestservice.dto;

import com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels.PointOfInterest;

public class PointOfInterestDTO {
    private Long poi_id;
    private String type; 
    private String name; 
    private String description; 
    private String poi_address;

    public static PointOfInterestDTO fromEntity(PointOfInterest pointOfInterestEntity) {
        PointOfInterestDTO pointOfInterestDTO = new PointOfInterestDTO();
        pointOfInterestDTO.setPoi_id(pointOfInterestEntity.getPoi_id());
        pointOfInterestDTO.setType(pointOfInterestEntity.getType());
        pointOfInterestDTO.setName(pointOfInterestEntity.getName());
        pointOfInterestDTO.setDescription(pointOfInterestEntity.getDescription());
        pointOfInterestDTO.setPoiAddress(pointOfInterestEntity.getPoi_address());
        return pointOfInterestDTO;
    }
    // Getters and Setters

    public Long getPoi_id() {
        return poi_id;
    }

    public void setPoi_id(Long poi_id) {
        this.poi_id = poi_id;
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

    public String getPoiAddress() {
        return poi_address;
    }

    public void setPoiAddress(String poi_address) {
        this.poi_address = poi_address;
    }
}
