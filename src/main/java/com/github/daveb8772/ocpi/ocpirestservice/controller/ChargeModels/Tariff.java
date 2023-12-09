package com.github.daveb8772.ocpi.ocpirestservice.controller.ChargeModels;

public class Tariff {

    private String tariffId; // Unique identifier for the tariff
    private String tariffName; // Name of the tariff
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
