package com.github.daveb8772.ocpi.ocpirestservice.controller.Models.DepotModels;

import com.github.daveb8772.ocpi.ocpirestservice.controller.Models.ChargeModels.ChargingSession;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tariff_changes")
public class TariffChange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "charging_session_id")
    private ChargingSession chargingSession;

    @Column(name = "change_timestamp", nullable = false)
    private LocalDateTime changeTimestamp;

    @ManyToOne
    @JoinColumn(name = "old_tariff_id")
    private Tariff oldTariff;

    @ManyToOne
    @JoinColumn(name = "new_tariff_id")
    private Tariff newTariff;

    // Constructors, getters, and setters


    public TariffChange(LocalDateTime changeTimestamp) {
        this.changeTimestamp = changeTimestamp;
    }

    public TariffChange(LocalDateTime changeTimestamp, Tariff oldTariff, Tariff newTariff) {
        this.changeTimestamp = changeTimestamp;
        this.oldTariff = oldTariff;
        this.newTariff = newTariff;
    }

    public Tariff getNewTariff() {

        return newTariff;
    }

    public Tariff getOldTariff() {

        return oldTariff;
    }


    public LocalDateTime getChangeTimestamp() {
        return changeTimestamp;
    }

    public void setChangeTimestamp(LocalDateTime changeTimestamp) {
        this.changeTimestamp = changeTimestamp;
    }
}
