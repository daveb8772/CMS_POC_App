package com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels;

import jakarta.persistence.*;

@Entity
@Table(name = "Tariff")
public class Tariff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tariffId; // Unique identifier for the tariff

    @Column(name = "tariff_name") // Define the column name for the tariff name
    private String tariffName; // Name of the tariff

    @Column(name = "price") // Define the column name for the price
    private double price; // Price per kWh for the tariff


    public Tariff() {}

    // Constructor that initializes all fields
    public Tariff( String tariffName, double price) {

        this.tariffName = tariffName;
        this.price = price;
    }


    public Long getTariffId() {
        return tariffId;
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
