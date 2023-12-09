package com.github.daveb8772.ocpi.ocpirestservice.controller.DepotModels;

public class Address {

    private String id;
    private String formattedAddress; // Full address as it would be displayed on a map
    private String[] addressLines; // Array of individual address lines
    private String addressType; // Type of address (e.g., residential, commercial)
    private String locality; // City or town name
    private String postalCode; // Postal code or ZIP code
    private String countryCode; // ISO 3166-1 alpha-2 country code

    // Add latitude and longitude fields
    private String latitude;
    private String longitude;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public String[] getAddressLines() {
        return addressLines;
    }

    public void setAddressLines(String[] addressLines) {
        this.addressLines = addressLines;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    // Getter and setter for latitude
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    // Getter and setter for longitude
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
