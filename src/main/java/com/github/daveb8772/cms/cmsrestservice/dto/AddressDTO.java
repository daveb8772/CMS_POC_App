
package com.github.daveb8772.cms.cmsrestservice.dto;

import com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels.Address;

public class AddressDTO {

    private String formattedAddress;
    private String locality;
    private String postalCode;
    private String countryCode;
    private String latitude;
    private String longitude;

    public static AddressDTO fromEntity(Address addressEntity) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setFormattedAddress(addressEntity.getFormattedAddress());
        addressDTO.setLocality(addressEntity.getLocality());
        addressDTO.setPostalCode(addressEntity.getPostalCode());
        addressDTO.setCountryCode(addressEntity.getCountryCode());
        addressDTO.setLatitude(addressEntity.getLatitude());
        addressDTO.setLongitude(addressEntity.getLongitude());
        return addressDTO;
    }
    // Constructors, getters, and setters

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
