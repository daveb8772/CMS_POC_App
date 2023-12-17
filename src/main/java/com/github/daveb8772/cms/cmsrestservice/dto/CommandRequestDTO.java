package com.github.daveb8772.cms.cmsrestservice.dto;

import com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels.CommandRequest;
import java.time.LocalDateTime;

public class CommandRequestDTO {
    private Long id;
    private String commandType;
    private String commandParameter;
    private LocalDateTime timestamp;
    private String chargingPointId; // Assuming you want to include the ID of the associated ChargingPoint


    public static CommandRequestDTO fromEntity(CommandRequest commandRequest) {
        CommandRequestDTO dto = new CommandRequestDTO();
        dto.setId(commandRequest.getId());
        dto.setCommandType(commandRequest.getCommandType().toString());
        dto.setCommandParameter(commandRequest.getCommandParameter());
        dto.setTimestamp(commandRequest.getTimestamp());
        if (commandRequest.getChargingPointId() != null) {
            dto.setChargingPointId(commandRequest.getChargingPointId());
        }
        return dto;
    }

// Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommandType() {
        return commandType;
    }

    public void setCommandType(String commandType) {
        this.commandType = commandType;
    }

    public String getCommandParameter() {
        return commandParameter;
    }

    public void setCommandParameter(String commandParameter) {
        this.commandParameter = commandParameter;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getChargingPointId() {
        return chargingPointId;
    }

    public void setChargingPointId(String chargingPointId) {
        this.chargingPointId = chargingPointId;
    }

}
