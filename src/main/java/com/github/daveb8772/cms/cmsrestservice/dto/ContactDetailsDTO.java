
package com.github.daveb8772.cms.cmsrestservice.dto;

import com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels.ContactDetails;

public class ContactDetailsDTO {
    private String contactId; 
    private String phone; 
    private String email; 
    private String website; 
    private String languages;

    public static ContactDetailsDTO fromEntity(ContactDetails contactDetailsEntity) {
        ContactDetailsDTO contactDetailsDTO = new ContactDetailsDTO();
        contactDetailsDTO.setContactId(contactDetailsEntity.getContactId());
        contactDetailsDTO.setPhone(contactDetailsEntity.getPhone());
        contactDetailsDTO.setEmail(contactDetailsEntity.getEmail());
        contactDetailsDTO.setWebsite(contactDetailsEntity.getWebsite());
        contactDetailsDTO.setLanguages(contactDetailsEntity.getLanguages());
        return contactDetailsDTO;
    }
    // Getters and Setters

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
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
