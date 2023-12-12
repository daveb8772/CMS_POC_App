package com.github.daveb8772.ocpi.ocpirestservice.controller.Models.ChargeModels;

import jakarta.persistence.*;

@Entity
@Table(name = "charging_session_errors")
public class SessionError {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Column(name = "error_code")
    private String errorCode; // For example, "E001"

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Column(name = "error_message")
    private String errorMessage; // Description of the error

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "charging_session_id")
    private ChargingSession chargingSession;

    // Constructors, getters, and setters
}
