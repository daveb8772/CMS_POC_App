package com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.github.daveb8772.cms.cmsrestservice.utility.MockDataGenerator;
import jakarta.persistence.*;


@Entity
@Table(name = "charging_sessions")
public class ChargingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cp_id")
    private String cpId;

    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "status")
    private String status;

    @Column(name = "connector_id")
    private String connectorId;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "current_power")
    private double currentPower;

    @Column(name = "energy_delivered")
    private double energyDelivered;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "chargingSession")
    private List<SessionError> errors;

    @ManyToOne
    @JoinColumn(name = "current_tariff_id")
    private Tariff currentTariff;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "chargingSession")
    private List<TariffChange> tariffChanges;



    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "chargingSession")
    public List<MeterRecord> meterRecords;


    public ChargingSession(String cpId, String sessionId, String status, String connectorId, LocalDateTime startDate, LocalDateTime endDate) {
        this.cpId = cpId;
        this.sessionId = sessionId;
        this.status = status;
        this.connectorId = connectorId;
        this.startDate = startDate;
        this.endDate = endDate;

        // Initializing other fields with mock data from MockDataGenerator
        this.transactionId = MockDataGenerator.generateTransactionId();
        this.currentPower = MockDataGenerator.generateCurrentPower();
        this.energyDelivered = MockDataGenerator.generateEnergyDelivered();
        this.errors = MockDataGenerator.generateErrors(); // Empty list or add some mock errors
        this.currentTariff = MockDataGenerator.generateTariff(); // Create a mock Tariff object
        this.tariffChanges = MockDataGenerator.generateTariffChanges(); // Empty list or add some mock TariffChange objects
        this.meterRecords = MockDataGenerator.generateMeterRecords(); // Empty list or add some mock MeterRecord objects
    }



    public ChargingSession() {}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
         this.id=id;
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

    public List<SessionError> getErrors() {
        return errors;
    }

    public void setErrors(List<SessionError> errors) {
        this.errors = errors;
    }

    public Tariff getCurrentTariff() {
        return currentTariff;
    }

    public void setCurrentTariff(Tariff currentTariff) {
        this.currentTariff = currentTariff;
    }

    public List<TariffChange> getTariffChanges() {
        return tariffChanges;
    }


    public void setTariffChanges(List<TariffChange> tariffChanges) {
        this.tariffChanges = tariffChanges;
    }

    public void addTariffChange(TariffChange tariffChange) {
        if (tariffChanges == null) {
            tariffChanges = new ArrayList<>();
        }

        tariffChanges.add(tariffChange);
    }

    public void addMeterRecords(MeterRecord meterRecord) {
        if (meterRecords == null) {
            meterRecords = new ArrayList<>();
        }

        meterRecords.add(meterRecord);
    }

    public List<MeterRecord> getMeterRecords() {
        return meterRecords;
    }
    public double getDeltaMeterValueBetweenTimestamps(LocalDateTime startTimestamp, LocalDateTime endTimestamp) {
        double startValue = 0.0;
        double endValue = 0.0;

        for (MeterRecord record : meterRecords) {
            if (!record.recordTimestamp.isAfter(startTimestamp)) {
                startValue = record.energyDelivered;
            }
            if (!record.recordTimestamp.isAfter(endTimestamp)) {
                endValue = record.energyDelivered;
            }
        }

        return endValue - startValue;
    }
    public double getLastMeterValueAtTimestamp(LocalDateTime targetTimestamp) {
        for (int i = meterRecords.size() - 1; i >= 0; i--) {
            MeterRecord record = meterRecords.get(i);
            if (!record.recordTimestamp.isAfter(targetTimestamp)) {
                return record.energyDelivered;
            }
        }
        // Handle the case where there is no record before the target timestamp
        throw new IllegalArgumentException("No record found before the target timestamp");
    }

    public double calculateTotalChargedAmount() {
        double totalChargedAmount = 0.0;

        for (TariffChange tariffChange : tariffChanges) {
            LocalDateTime startTimestamp = tariffChange.getChangeTimestamp();
            LocalDateTime endTimestamp = getNextTariffChangeTimestamp(tariffChange);
            double deltaMeterValue = getDeltaMeterValueBetweenTimestamps(startTimestamp, endTimestamp);
            double tariffPrice = tariffChange.getNewTariff().getPrice();
            double chargedAmount = deltaMeterValue * tariffPrice;
            totalChargedAmount += chargedAmount;
        }

        return totalChargedAmount;
    }

    // method to find the next tariff change timestamp
// This method assumes that the next change timestamp is the timestamp of the next tariff change in the list.
    private LocalDateTime getNextTariffChangeTimestamp(TariffChange currentChange) {
        int currentIndex = tariffChanges.indexOf(currentChange);
        if (currentIndex < tariffChanges.size() - 1) {
            return tariffChanges.get(currentIndex + 1).getChangeTimestamp();
        } else {
            // Handle the case where there is no next tariff change
            return currentChange.getChangeTimestamp();
        }
    }


}
