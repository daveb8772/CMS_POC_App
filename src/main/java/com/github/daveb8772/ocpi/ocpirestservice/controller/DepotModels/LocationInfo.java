package com.github.daveb8772.ocpi.ocpirestservice.controller.DepotModels;


import java.util.List;

public class LocationInfo {


        private String id;
        private Address address;
        private String name;
        private String description;
        private String openingHours;
        private ContactDetails contactInformation;
        private Accessibility accessibilityInformation;
        private List<PointOfInterest> pointsOfInterest;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getOpeningHours() {
            return openingHours;
        }

        public void setOpeningHours(String openingHours) {
            this.openingHours = openingHours;
        }

        public ContactDetails getContactInformation() {
            return contactInformation;
        }

        public void setContactInformation(ContactDetails contactInformation) {
            this.contactInformation = contactInformation;
        }

        public Accessibility getAccessibilityInformation() {
            return accessibilityInformation;
        }

        public void setAccessibilityInformation(Accessibility accessibilityInformation) {
            this.accessibilityInformation = accessibilityInformation;
        }

        public List<PointOfInterest> getPointsOfInterest() {
            return pointsOfInterest;
        }

        public void setPointsOfInterest(List<PointOfInterest> pointsOfInterest) {
            this.pointsOfInterest = pointsOfInterest;
        }
    }

