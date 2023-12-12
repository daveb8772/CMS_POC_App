package com.github.daveb8772.ocpi.ocpirestservice.controller.Models.DepotModels;

import jakarta.persistence.*;

@Entity
@Table(name = "Tariff")
public class Tariff {

    @Id
    @Column(name = "tariff_id") // Define the column name for the ID
    private String tariffId; // Unique identifier for the tariff

    @Column(name = "tariff_name") // Define the column name for the tariff name
    private String tariffName; // Name of the tariff

    @Column(name = "price") // Define the column name for the price
    private double price; // Price per kWh for the tariff

    public Tariff() {}

    // Constructor that initializes all fields
    public Tariff(String tariffId, String tariffName, double price) {
        this.tariffId = tariffId;
        this.tariffName = tariffName;
        this.price = price;
    }


    public String getTariffId() {
        return tariffId;
    }

    public void setTariffId(String tariffId) {
        this.tariffId = tariffId;
    }

    public String getTariffName() {
        return tariffName;
    }

    public void setTariffName(String tariffName) {
        this.tariffName = tariffName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


}
