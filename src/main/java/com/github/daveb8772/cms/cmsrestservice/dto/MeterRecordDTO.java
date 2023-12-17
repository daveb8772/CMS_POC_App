package com.github.daveb8772.cms.cmsrestservice.dto;

import com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels.MeterRecord;

import java.time.LocalDateTime;

public class MeterRecordDTO {

    private Long id;
    private LocalDateTime recordTimestamp;
    private double energyDelivered;

    // Constructors
    public MeterRecordDTO() {}

    public MeterRecordDTO(Long id, LocalDateTime recordTimestamp, double energyDelivered) {
        this.id = id;
        this.recordTimestamp = recordTimestamp;
        this.energyDelivered = energyDelivered;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getRecordTimestamp() {
        return recordTimestamp;
    }

    public void setRecordTimestamp(LocalDateTime recordTimestamp) {
        this.recordTimestamp = recordTimestamp;
    }

    public double getEnergyDelivered() {
        return energyDelivered;
    }

    public void setEnergyDelivered(double energyDelivered) {
        this.energyDelivered = energyDelivered;
    }

    // Static method to convert entity to DTO
    public static MeterRecordDTO fromEntity(MeterRecord entity) {
        return new MeterRecordDTO(
                entity.getId(),
                entity.getRecordTimestamp(),
                entity.getEnergyDelivered()
        );
    }
}
