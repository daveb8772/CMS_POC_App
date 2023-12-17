package com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "command_requests")
public class CommandRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Enumerated(EnumType.STRING)
    @Column(name = "command_type", nullable = false)
    private CommandType commandType; // Enum representing the type of OCPI command

    @NotBlank
    @Column(name = "command_parameter", nullable = false)
    private String commandParameter; // Specific parameter for the command

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp; // Timestamp for the command request

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "charging_point_id") // This should match the column name that holds the foreign key
    private ChargingPoint chargingPoint;

    // Constructors, getters, and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChargingPoint getChargingPoint() {
        return chargingPoint;
    }

    public void setChargingPoint(ChargingPoint chargingPoint) {
        this.chargingPoint = chargingPoint;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(CommandType commandType) {
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


    public enum CommandType {
        START_SESSION("StartSession"),
        STOP_SESSION("StopSession"),
        UNLOCK_CONNECTOR("UnlockConnector"),
        CANCEL_RESERVATION("CancelReservation"),
        RESERVE_NOW("ReserveNow");

        private final String value;

        CommandType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
