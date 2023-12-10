package com.github.daveb8772.ocpi.ocpirestservice.utility;

import com.github.daveb8772.ocpi.ocpirestservice.controller.ChargeModels.*;
import com.github.daveb8772.ocpi.ocpirestservice.controller.DepotModels.*;
import com.github.daveb8772.ocpi.ocpirestservice.controller.ResponseModels.*;
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
    public static List<ChargingSessionDataResponse> generateChargingSessions(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> new ChargingSessionDataResponse(
                        IntStream.range(0, 5) // Generate 5 ChargingSession objects per ChargingSessionDataResponse
                                .mapToObj(j -> generateChargingSession())
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }



    private static ChargingPoint generateChargingPoint() {
        ChargingPoint chargingPoint = new ChargingPoint();
        chargingPoint.setChargingPointId(faker.idNumber().valid());
        chargingPoint.setRfId(faker.idNumber().valid());
        chargingPoint.setStatus(faker.options().option("Available", "Occupied", "OutOfService"));
        chargingPoint.setLocation(generateLocationInfo());
        chargingPoint.setDispensers(generateDispensers());
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

        return chargingPoint;
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

    public static List<Error> generateErrors() {
        // Return an empty list or create mock errors
        return new ArrayList<>();
    }

    public static Tariff generateTariff() {
        return new Tariff(UUID.randomUUID().toString(),
                "Tariff" + UUID.randomUUID(),
                faker.number().randomDouble(2, 1, 100));
    }

    public static List<ChargingSession.TariffChange> generateTariffChanges() {
        // Return an empty list or create mock TariffChange objects
        return new ArrayList<>();
    }

    public static List<ChargingSession.MeterRecord> generateMeterRecords() {
        // Return an empty list or create mock MeterRecord objects
        return new ArrayList<>();
    }
    public static LocationInfo generateLocationInfo() {
        LocationInfo locationInfo = new LocationInfo();
        locationInfo.setId(UUID.randomUUID().toString());


        Address address = new Address();
        address.setFormattedAddress(faker.address().fullAddress()); // or separate fields like street, city, etc.
        address.setLatitude(faker.address().latitude());
        address.setLongitude(faker.address().longitude());

        locationInfo.setAddress(address);
        locationInfo.setName(faker.company().name());
        locationInfo.setDescription(faker.lorem().paragraph());
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
                    poi.setId(faker.company().name());
                    poi.setType(faker.lorem().sentence());

                    return poi;
                })
                .collect(Collectors.toList());
        locationInfo.setPointsOfInterest(pointsOfInterest);

        return locationInfo;
    }

    private static Set<Dispenser> generateDispensers() {
        return IntStream.range(0, faker.number().numberBetween(1, 4))
                .mapToObj(i -> {
                    Dispenser dispenser = new Dispenser();
                    // Assuming Dispenser has a method setId, setType, etc.
                    dispenser.setId(UUID.randomUUID().toString());
                    dispenser.setConnectorType(new ConnectorType(ConnectorType.ConnectorTypeValue.TYPE1));
                    dispenser.setStatus(faker.options().option("Available", "InUse", "OutOfOrder"));
                    return dispenser;
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

    public static List<TariffDataResponse> generateTariffs(int count) {
        return IntStream.range(0, count).mapToObj(i -> {
            TariffDataResponse tariffDataResponse = new TariffDataResponse();

            // Creating a list of Tariff objects with mock data
            List<Tariff> tariffs = IntStream.range(0, 3) // Let's assume each response has 3 tariffs
                    .mapToObj(j -> new Tariff(
                            faker.idNumber().valid(),   // tariffId
                            faker.commerce().productName(), // tariffName
                            faker.number().randomDouble(2, 1, 3))) // price
                    .collect(Collectors.toList());

            // Setting the list of Tariff objects in TariffDataResponse
            tariffDataResponse.setTariffs(tariffs);

            // Set a mock status
            Status status = new Status(200, "Active Tariff");
            tariffDataResponse.setStatus(status);

            return tariffDataResponse;
        }).collect(Collectors.toList());
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

    public static List<ChargingPointDataResponse> generateChargingPoints(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> {
                    List<ChargingPoint> chargingPoints = IntStream.range(0, 5) // Assuming 5 ChargingPoints per response
                            .mapToObj(j -> generateChargingPoint())
                            .collect(Collectors.toList());

                    ChargingPointDataResponse chargingPointDataResponse = new ChargingPointDataResponse();
                    chargingPointDataResponse.setChargingPoints(chargingPoints);
                    chargingPointDataResponse.setStatus(new Status(HttpStatus.OK.value(), "All systems functional")); // Example status
                    return chargingPointDataResponse;
                })
                .collect(Collectors.toList());
    }

    public static UserInfoResponse generateUserInfo(String userId) {
        Faker faker = new Faker();
        return new UserInfoResponse(
                userId,
                faker.name().fullName(),
                faker.internet().emailAddress(),
                faker.phoneNumber().phoneNumber(),
                faker.options().option("en", "es", "de")
        );
    }

    public static AuthorizationResponse generateAuthorizationResponse(boolean authorized) {
        Faker faker = new Faker();
        return new AuthorizationResponse(
                authorized,
                faker.internet().password(), // Assuming this is an accessToken
                ZonedDateTime.now().plusDays(30), // Assuming a token expiry of 30 days
                faker.lorem().characters(), // Assuming this as a refreshToken
                null // Errors could be added based on the scenario
        );
    }

}
