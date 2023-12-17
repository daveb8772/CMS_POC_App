package com.github.daveb8772.cms.cmsrestservice.utility;

import com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels.*;
import com.github.daveb8772.cms.cmsrestservice.controller.Models.ResponseModels.*;

import org.springframework.http.HttpStatus;
import com.github.javafaker.Faker;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class MockDataGenerator {
    public static final Faker faker = new Faker();

    private static ChargingSession generateChargingSession() {
        return new ChargingSession(
                faker.random().toString(),
                faker.random().toString(),
                faker.options().option("In progress", "Completed", "Error"),
                faker.idNumber().valid(),
                LocalDateTime.now().minusHours(faker.number().numberBetween(1, 24)),
                LocalDateTime.now()
        );
    }
    public static List<ChargingSession> generateChargingSessions(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> generateChargingSession())
                .collect(Collectors.toList());
    }



    private static List<String> generateTariffIds(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> faker.idNumber().valid())
                .collect(Collectors.toList());
    }

    public static String generateTransactionId() {
        return UUID.randomUUID().toString();
    }

    public static double generateCurrentPower() {
        return faker.number().randomDouble(2, 1, 22); // Random value between 1 and 22 kW
    }

    public static double generateEnergyDelivered() {
        return faker.number().randomDouble(2, 1, 100); // Random value between 1 and 100 kWh
    }

    public static List<SessionError> generateErrors() {
        return IntStream.range(0, faker.number().numberBetween(0, 3)) // Generate 0 to 3 errors
                .mapToObj(i -> {
                    SessionError error = new SessionError();
                    error.setErrorCode("E" + faker.number().numberBetween(100, 999)); // Error code like E101, E202, etc.
                    error.setErrorMessage(faker.lorem().sentence()); // Random error message
                    // Don't set chargingSession here as it will be set when linking to a ChargingSession
                    return error;
                })
                .collect(Collectors.toList());
    }
    public static Tariff generateTariff() {
        return new Tariff(UUID.randomUUID().toString(),
                "Tariff" + UUID.randomUUID(),
                faker.number().randomDouble(2, 1, 100));
    }

    public static List<TariffChange> generateTariffChanges() {
        // Return an empty list or create mock TariffChange objects
        return new ArrayList<>();
    }

    public static List<MeterRecord> generateMeterRecords() {
        // Return an empty list or create mock MeterRecord objects
        return new ArrayList<>();
    }


    public static LocationInfo generateLocationInfo() {
        LocationInfo locationInfo = new LocationInfo();
        locationInfo.setLocation_id(UUID.randomUUID().toString());


        Address address = new Address();
        address.setFormattedAddress(faker.address().fullAddress()); // or separate fields like street, city, etc.
        address.setLatitude(faker.address().latitude());
        address.setLongitude(faker.address().longitude());

        locationInfo.setAddress(address);
        locationInfo.setName(faker.company().name());
        locationInfo.setDetails(faker.lorem().paragraph());
        locationInfo.setOpeningHours("24/7"); // or however you format opening hours

        ContactDetails contactDetails = new ContactDetails();
        contactDetails.setEmail(faker.internet().emailAddress());
        contactDetails.setPhone(faker.phoneNumber().phoneNumber());
        locationInfo.setContactInformation(contactDetails);
        Accessibility accessibility = new Accessibility();
        // Set appropriate fields for accessibility here
        locationInfo.setAccessibilityInformation(accessibility);

        // Generate a list of points of interest
        List<PointOfInterest> pointsOfInterest = IntStream.range(0, 3)
                .mapToObj(i -> {
                    PointOfInterest poi = new PointOfInterest();
                    poi.setPoi_id(UUID.randomUUID().toString());
                    poi.setName(faker.company().name());
                    poi.setType(faker.lorem().sentence());

                    return poi;
                })
                .collect(Collectors.toList());
        locationInfo.setPointsOfInterest(pointsOfInterest);
        // Generate a set of ChargingPoints and associate them with this LocationInfo
        List<ChargingPoint> chargingPoints = generateChargingPoints(5);
        locationInfo.setChargingPoints(chargingPoints);

        return locationInfo;
    }

    private static Set<Connector> generateDispensers(ChargingPoint chargingPoint) {
        return IntStream.range(0, faker.number().numberBetween(1, 4))
                .mapToObj(i -> {
                    Connector connector = new Connector();
                    connector.setId(UUID.randomUUID().toString());

                    connector.setConnectorType(ConnectorCapabilities.ConnectorType.valueOf(faker.options().option("TYPE1", "TYPE2", "CCS_COMBO_2", "CHAdeMO", "TESLA")));
                    connector.setChargingMode(ConnectorCapabilities.ChargingModeValue.valueOf(faker.options().option("AC", "DC", "FAST_AC", "FAST_DC")));
                    connector.setStatus(faker.options().option("Available", "InUse", "OutOfOrder"));
                    connector.setPower(faker.number().randomDouble(2, 7, 22)); // Random value
                    connector.setOccupied(faker.bool().bool());
                    connector.setCurrentChargingSessionId(null); // No active session initially
                    connector.setChargingPoint(chargingPoint); // Link back to the charging point
                    return connector;
                })
                .collect(Collectors.toSet());
    }

    private static ChargingPoint generateChargingPoint() {
        ChargingPoint chargingPoint = new ChargingPoint();
        chargingPoint.setChargingPointId(faker.idNumber().valid());
        chargingPoint.setRfId(faker.idNumber().valid());
        chargingPoint.setStatus(faker.options().option("Available", "Occupied", "OutOfService"));
        chargingPoint.setLocation(generateLocationInfo());

        chargingPoint.setConnectorType(faker.options().option("Type1", "Type2", "CCS", "CHAdeMO"));
        chargingPoint.setConnectorStatus(faker.options().option("Connected", "Available", "Faulted"));
        chargingPoint.setConnectorPower(faker.number().randomDouble(2, 7, 22)); // Random value between 7 and 22 kW
        chargingPoint.setAvailabilityStatus(faker.options().option("InService", "OutOfService", "Scheduled"));
        chargingPoint.setCurrentUtilization(faker.number().randomDouble(2, 0, 1)); // Random value between 0 and 1
        chargingPoint.setLastUpdate(ZonedDateTime.now().minusMinutes(faker.number().numberBetween(0, 60)));
        chargingPoint.setChargingProfiles(generateChargingProfiles());
        List<String> tariffIds = generateTariffIds(3); // Generate 3 tariff IDs for each charging point
        chargingPoint.setTariffIds(generateTariffIds(3)); // Set the list of tariff IDs

        chargingPoint.setMaxChargingPower(faker.number().randomDouble(2, 10, 50)); // Random value between 10 and 50 kW
        chargingPoint.setConnectorCapabilities(generateConnectorCapabilities());

        Set<Connector> connectors = generateDispensers(chargingPoint);
        chargingPoint.setConnectors(connectors);

        return chargingPoint;
    }

    public static List<ChargingPoint> generateChargingPoints(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> generateChargingPoint())
                .collect(Collectors.toList());
    }



    private static List<ChargingProfile> generateChargingProfiles() {
        return IntStream.range(0, faker.number().numberBetween(1, 3))
                .mapToObj(i -> {
                    ChargingProfile chargingProfile = new ChargingProfile();
                    // Assuming ChargingProfile has methods setId, setMaxChargingPower, etc.
                    chargingProfile.setId(UUID.randomUUID().toString());
                    chargingProfile.setMaxChargingPower(faker.number().randomDouble(2, 0, 22));
                    return chargingProfile;
                })
                .collect(Collectors.toList());
    }

    public static List<Tariff> generateTariffs(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> new Tariff(
                        faker.idNumber().valid(),   // tariffId
                        faker.commerce().productName(), // tariffName
                        faker.number().randomDouble(2, 1, 3))) // price
                .collect(Collectors.toList());
    }


    public static ConnectorCapabilities generateConnectorCapabilities() {
        ConnectorCapabilities capabilities = new ConnectorCapabilities();

        capabilities.setBidirectionalCharging(faker.bool().bool());
        capabilities.setDynamicPowerSharing(faker.bool().bool());
        /*
        capabilities.setChargeMode(ConnectorCapabilities.ChargingModeValue.DC);
        capabilities.setPlugType(ConnectorTypeValue.CCS);
        capabilities.setMaximumPower(faker.number().randomDouble(2, 0, 350)); // Power in kW
        capabilities.setCommunicationProtocol(CommunicationProtocolValue));
*/
        // Additional features can be a set of strings representing miscellaneous capabilities
        Set<String> additionalFeatures = Stream.of(
                "Temperature Regulation",
                "Cable Lock",
                "LED Indicator",
                "Solar Charging",
                "Wireless Charging"
        ).collect(Collectors.toSet());

        capabilities.setAdditionalFeatures(additionalFeatures);

        return capabilities;
    }

    public static List<CommandRequest> generateCommandRequests(int count, ChargingPoint chargingPoint) {
        return IntStream.range(0, count)
                .mapToObj(i -> {
                    CommandRequest commandRequest = new CommandRequest();
                    commandRequest.setCommandType(CommandRequest.CommandType.values()[new Random().nextInt(CommandRequest.CommandType.values().length)]);
                    commandRequest.setCommandParameter("Parameter " + i);
                    commandRequest.setTimestamp(LocalDateTime.now().minusHours(i));
                    return commandRequest;
                })
                .collect(Collectors.toList());
    }



    public static Authorization generateAuthorization(boolean authorized) {
        Faker faker = new Faker();
        Authorization authorization = new Authorization();
        authorization.setIsAuthorized(authorized);
        authorization.setAccessToken(faker.internet().password()); // Assuming this is an accessToken
        authorization.setExpiresAt(ZonedDateTime.now().plusDays(30)); // Token expiry of 30 days
        authorization.setRefreshToken(faker.lorem().characters()); // Assuming this as a refreshToken

        return authorization;
    }


}
