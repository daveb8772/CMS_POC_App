package com.github.daveb8772.cms.cmsrestservice.utility;

import com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels.*;

import com.github.javafaker.Faker;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class MockDataGenerator {
    public static Faker faker = new Faker(new Locale("en-US")); // specify the locale



    private static List<String> generateTariffIds(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> faker.idNumber().valid())
                .collect(Collectors.toList());
    }

    public static Tariff generateTariff() {
        Tariff tempTariff = new Tariff(ThreadLocalRandom.current().nextLong(),
                "Tariff" + UUID.randomUUID(),
                faker.number().randomDouble(2, 1, 100));
        return tempTariff;
    }

    public static List<Tariff> generateTariffs(int count) {
        List<Tariff> tariffs = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            tariffs.add(generateTariff());
        }
        return tariffs;
    }

    public static List<ChargingSession> generateChargingSessions(int count, List<Tariff> tariffs) {
        if (tariffs == null || tariffs.isEmpty()) {
            throw new IllegalArgumentException("The list of tariffs cannot be null or empty.");
        }

        return IntStream.range(0, count)
                .mapToObj(i -> generateChargingSession(tariffs))
                .collect(Collectors.toList());
    }

    private static ChargingSession generateChargingSession(List<Tariff> tariffs) {
        ChargingSession session = new ChargingSession(
                faker.random().toString(),
                faker.random().toString(),
                faker.options().option("In progress", "Completed", "Error"),
                faker.idNumber().valid(),
                LocalDateTime.now().minusHours(faker.number().numberBetween(1, 24)),
                LocalDateTime.now()
        );

        // Randomly select a Tariff from the provided list
        Tariff randomTariff = tariffs.get(faker.random().nextInt(tariffs.size()));
        session.setCurrentTariff(randomTariff);

        // Initialize other fields with mock data from MockDataGenerator
        // ...

        return session;
    }

    private static ChargingPoint generateChargingPoint() {
        ChargingPoint chargingPoint = new ChargingPoint();
        chargingPoint.setChargingPointId(ThreadLocalRandom.current().nextLong());
        chargingPoint.setStatus(faker.options().option("Available", "Occupied", "OutOfService"));


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

    public static List<TariffChange> generateTariffChanges() {
        // Return an empty list or create mock TariffChange objects
        return new ArrayList<>();
    }

    public static List<MeterRecord> generateMeterRecords() {
        // Return an empty list or create mock MeterRecord objects
        return new ArrayList<>();
    }

    public static Address generateAddress() {
        Address address = new Address();

        // Use Faker to generate address components
        String formattedAddress = String.format("%s, %s, %s, %s, %s",
                "Street Address",
                "Secondary Street Address",
                "City Name", // Replace with a static value or another working Faker API
                faker.address().stateAbbr(),
                faker.address().zipCode());

        address.setFormattedAddress(formattedAddress);
        address.setLocality("Locality"); // Static value or use Faker
        address.setPostalCode(faker.address().zipCode());
        address.setCountryCode(faker.country().countryCode2()); // 2-letter country code
        //address.setLatitude(faker.address().latitude());
        //address.setLongitude(faker.address().longitude());

        return address;
    }

    public static LocationInfo generateLocationInfo(List<ChargingPoint> chargingPoints) {
        LocationInfo locationInfo = new LocationInfo();
        locationInfo.setLocation_id(ThreadLocalRandom.current().nextLong());

        Address address = generateAddress();
        locationInfo.setAddress(address);
        //locationInfo.setName(faker.number().toString());//faker.company().name());
        String name = faker.company().name().replaceAll("\\s+.*", "") + "-" + faker.number().numberBetween(1, 100);
        locationInfo.setName(name);
        locationInfo.setDetails(faker.lorem().paragraph());
        locationInfo.setOpeningHours("24/7"); // or however you format opening hours

        ContactDetails contactDetails = new ContactDetails();
        contactDetails.setEmail(faker.lorem().toString());//faker.internet().emailAddress());
        contactDetails.setPhone(faker.lorem().toString());//faker.phoneNumber().phoneNumber());
        locationInfo.setContactInformation(contactDetails);
        Accessibility accessibility = new Accessibility();
        // Set appropriate fields for accessibility here
        locationInfo.setAccessibilityInformation(accessibility);

        // Generate a list of points of interest
        List<PointOfInterest> pointsOfInterest = generatePointsOfInterest(1);
        locationInfo.setPointsOfInterest(pointsOfInterest);
        // Set the list of ChargingPoints
        locationInfo.setChargingPoints(chargingPoints);

        return locationInfo;
    }

    public static List<PointOfInterest> generatePointsOfInterest(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> {
                    PointOfInterest poi = new PointOfInterest();
                    poi.setPoi_id(ThreadLocalRandom.current().nextLong());
                    poi.setName(faker.lorem().toString());//faker.company().name()); // Generate a random company name
                    poi.setType(faker.lorem().word()); // Generate a random word for the type
                    poi.setDescription(faker.lorem().sentence()); // Generate a random sentence for the description

                    poi.setPoi_address(faker.lorem().toString());

                    return poi;
                })
                .collect(Collectors.toList());
    }

    private static Set<Connector> generateDispensers(ChargingPoint chargingPoint) {
        return IntStream.range(0, faker.number().numberBetween(1, 4))
                .mapToObj(i -> {
                    Connector connector = new Connector();
                    connector.setId(ThreadLocalRandom.current().nextLong());
                    ConnectorCapabilities connectorCapabilities = generateConnectorCapabilities();
                    connector.setConnectorType(connectorCapabilities.getPlugType());
                    connector.setChargingMode(connectorCapabilities.getChargeMode());
                    connector.setStatus(faker.options().option("Available", "InUse", "OutOfOrder"));
                    connector.setPower(faker.number().randomDouble(2, 7, 22)); // Random value
                    connector.setOccupied(faker.bool().bool());
                    connector.setCurrentChargingSessionId(null); // No active session initially
                    connector.setChargingPoint(chargingPoint); // Link back to the charging point
                    return connector;
                })
                .collect(Collectors.toSet());
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




    public static ConnectorCapabilities generateConnectorCapabilities() {
        ConnectorCapabilities capabilities = new ConnectorCapabilities();

        capabilities.setBidirectionalCharging(faker.bool().bool());
        capabilities.setDynamicPowerSharing(faker.bool().bool());
        capabilities.setChargeMode(ConnectorCapabilities.ChargingModeValue.DC);
        capabilities.setMaximumPower(faker.number().randomDouble(2, 0, 350)); // Power in kW

        // Randomly setting the plug type
        ConnectorCapabilities.ConnectorType[] plugTypes = ConnectorCapabilities.ConnectorType.values();
        ConnectorCapabilities.ConnectorType randomPlugType = plugTypes[faker.number().numberBetween(0, plugTypes.length)];
        capabilities.setPlugType(randomPlugType);

        // Randomly setting the communication protocol
        ConnectorCapabilities.CommunicationProtocolValue[] protocolValues = ConnectorCapabilities.CommunicationProtocolValue.values();
        ConnectorCapabilities.CommunicationProtocolValue randomProtocol = protocolValues[faker.number().numberBetween(0, protocolValues.length)];
        capabilities.setCommunicationProtocol(randomProtocol);

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

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static List<UserCredentials> generateUsers(int count) {
        List<UserCredentials> users = new ArrayList<>();
        IntStream.range(0, count).forEach(i -> {
            UserCredentials user = new UserCredentials();
            user.setName("user" + i);
            user.setPassword(passwordEncoder.encode("password" + i));
            users.add(user);
        });
        return users;
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
