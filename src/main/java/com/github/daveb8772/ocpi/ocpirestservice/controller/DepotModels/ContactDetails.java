package com.github.daveb8772.ocpi.ocpirestservice.controller.DepotModels;

public class ContactDetails {

    private String id; // Unique identifier for the contact information
    private String phone; // Phone number
    private String email; // Email address
    private String website; // Website URL
    private String languages; // List of supported languages (ISO 639-1 codes)

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }
}
