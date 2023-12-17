package com.github.daveb8772.cms.cmsrestservice.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

// Assuming SessionErrorDTO, TariffDTO, MeterRecordDTO are also defined similar to ChargingSessionDTO
import com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels.ChargingSession;
import com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels.SessionError;
import com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels.Tariff;
import com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels.MeterRecord;

public class ChargingSessionDTO {

    private Long id;
    private String cpId;
    private String sessionId;
    private String status;
    private String connectorId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String transactionId;
    private double currentPower;
    private double energyDelivered;
    private List<SessionErrorDTO> errors;
    private TariffDTO currentTariff;
    private List<MeterRecordDTO> meterRecords;

    public static ChargingSessionDTO fromEntity(ChargingSession entity) {
        ChargingSessionDTO dto = new ChargingSessionDTO();

        dto.setId(entity.getId());
        dto.setCpId(entity.getCpId());
        dto.setSessionId(entity.getSessionId());
        dto.setStatus(entity.getStatus());
        dto.setConnectorId(entity.getConnectorId());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setTransactionId(entity.getTransactionId());
        dto.setCurrentPower(entity.getCurrentPower());
        dto.setEnergyDelivered(entity.getEnergyDelivered());

        if (entity.getErrors() != null) {
            dto.setErrors(entity.getErrors().stream()
                    .map(SessionErrorDTO::fromEntity)
                    .collect(Collectors.toList()));
        }

        if (entity.getCurrentTariff() != null) {
            dto.setCurrentTariff(TariffDTO.fromEntity(entity.getCurrentTariff()));
        }

        if (entity.getMeterRecords() != null) {
            dto.setMeterRecords(entity.getMeterRecords().stream()
                    .map(MeterRecordDTO::fromEntity)
                    .collect(Collectors.toList()));
        }

        return dto;
    }
    // Standard getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpId() {
        return cpId;
    }

    public void setCpId(String cpId) {
        this.cpId = cpId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConnectorId() {
        return connectorId;
    }

    public void setConnectorId(String connectorId) {
        this.connectorId = connectorId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public double getCurrentPower() {
        return currentPower;
    }

    public void setCurrentPower(double currentPower) {
        this.currentPower = currentPower;
    }

    public double getEnergyDelivered() {
        return energyDelivered;
    }

    public void setEnergyDelivered(double energyDelivered) {
        this.energyDelivered = energyDelivered;
    }

    public List<SessionErrorDTO> getErrors() {
        return errors;
    }

    public void setErrors(List<SessionErrorDTO> errors) {
        this.errors = errors;
    }

    public TariffDTO getCurrentTariff() {
        return currentTariff;
    }

    public void setCurrentTariff(TariffDTO currentTariff) {
        this.currentTariff = currentTariff;
    }

    public List<MeterRecordDTO> getMeterRecords() {
        return meterRecords;
    }

    public void setMeterRecords(List<MeterRecordDTO> meterRecords) {
        this.meterRecords = meterRecords;
    }
}
