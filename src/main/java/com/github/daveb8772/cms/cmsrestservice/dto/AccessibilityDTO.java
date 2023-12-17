
package com.github.daveb8772.cms.cmsrestservice.dto;

import com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels.Accessibility;

public class AccessibilityDTO {
    private String accessibilityId; 
    private boolean isAccessible; 
    private String description; 
    private String additionalInformation;

    public static AccessibilityDTO fromEntity(Accessibility accessibilityEntity) {
        AccessibilityDTO accessibilityDTO = new AccessibilityDTO();
        accessibilityDTO.setAccessibilityId(accessibilityEntity.getAccessibilityId());
        accessibilityDTO.setAccessible(accessibilityEntity.isAccessible());
        accessibilityDTO.setDescription(accessibilityEntity.getDescription());
        accessibilityDTO.setAdditionalInformation(accessibilityEntity.getAdditionalInformation());
        return accessibilityDTO;
    }
    // Getters and Setters

    public String getAccessibilityId() {
        return accessibilityId;
    }

    public void setAccessibilityId(String accessibilityId) {
        this.accessibilityId = accessibilityId;
    }

    public boolean isAccessible() {
        return isAccessible;
    }

    public void setAccessible(boolean accessible) {
        isAccessible = accessible;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }
}
