package com.github.daveb8772.ocpi.ocpirestservice.controller.Models.ChargeModels;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "meter_records")
public class MeterRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "record_timestamp")
    public LocalDateTime recordTimestamp;

    @Column(name = "energy_delivered")
    public double energyDelivered;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "charging_session_id")
    private ChargingSession chargingSession;

    // Constructors, getters, and setters
}
