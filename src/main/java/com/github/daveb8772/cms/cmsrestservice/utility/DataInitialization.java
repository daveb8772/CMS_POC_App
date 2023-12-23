package com.github.daveb8772.cms.cmsrestservice.utility;

import com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels.*;
import com.github.daveb8772.cms.cmsrestservice.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Configuration
@Profile("dev")
public class DataInitialization {

    // Configurable properties
    @Value("${data.initialization.tariffCount}")
    private int tariffCount;
    @Value("${data.initialization.sessionCount}")
    private int sessionCount;
    @Value("${data.initialization.locationCount}")
    private int locationCount;
    @Value("${data.initialization.chargingPointCountPerLocation}")
    private int chargingPointCountPerLocation;
    @Value("${data.initialization.connectorCount}")
    private int connectorCount;
    @Value("${data.initialization.userCount}")
    private int userCount;
    @Value("${data.initialization.commandCount}")
    private int commandCount;
    @Value("${data.initialization.poiCount}")
    private int poiCount;


    private final ChargingSessionRepository chargingSessionRepository;
    private final ChargingPointRepository chargingPointRepository;
    private final CommandRequestRepository commandRequestRepository;
    private final AuthorizationRepository authorizationRepository;
    private final TariffRepository tariffRepository;
    private final LocationInfoRepository locationInfoRepository;

    private final UserRepository userRepository;

    public DataInitialization(ChargingSessionRepository chargingSessionRepository,
                              ChargingPointRepository chargingPointRepository,
                              CommandRequestRepository commandRequestRepository,
                              AuthorizationRepository authorizationRepository,
                              TariffRepository tariffRepository,
                              LocationInfoRepository locationInfoRepository,
                                UserRepository userRepository) {
        // Save references to repositories...
        this.chargingSessionRepository = chargingSessionRepository;
        this.authorizationRepository = authorizationRepository;
        this.tariffRepository = tariffRepository;
        this.chargingPointRepository = chargingPointRepository;
        this.commandRequestRepository = commandRequestRepository;
        this.locationInfoRepository = locationInfoRepository;
        this.userRepository = userRepository;
    }

    @Bean
    CommandLineRunner initDatabase() {

        return args -> {


            // Generate 5 UserCredentials entities
            List<UserCredentials> users = MockDataGenerator.generateUsers(5);

            // For each UserCredentials, generate an Authorization
            users.forEach(user -> {
                Authorization authorization = MockDataGenerator.generateAuthorization(true, user);
                user.setAuthorization(authorization); // Link Authorization to UserCredentials
                authorization.setUserCredentials(user); // Link UserCredentials to Authorization
            });

            // Now save the UserCredentials entities along with their linked Authorization
            userRepository.saveAll(users);

            // First, generate and save tariffs
            List<Tariff> generatedTariffs = MockDataGenerator.generateTariffs(tariffCount);
            tariffRepository.saveAll(generatedTariffs);

            // Generate and save ChargingPoint entities with commands
            // Loop to create 3 locations
            // Generate and save 5 ChargingPoint entities with commands for each location
            // Save LocationInfo with the associated ChargingPoints
            List<ChargingPoint> allChargingPoints = new ArrayList<>();
            IntStream.range(0, locationCount).mapToObj(i -> MockDataGenerator.generateChargingPoints(chargingPointCountPerLocation)).forEach(chargingPoints -> {
                chargingPoints.forEach(chargingPoint -> {
                    chargingPoint.setTariffs(generatedTariffs);
                    List<CommandRequest> commandRequests = MockDataGenerator.generateCommandRequests(commandCount, chargingPoint);
                    chargingPoint.setCommandRequests(commandRequests);
                    List<Connector> connectors = MockDataGenerator.generateConnectors(connectorCount,chargingPoint);
                    chargingPoint.setConnectors(connectors);
                    allChargingPoints.add(chargingPoint);

                });
                chargingPointRepository.saveAll(chargingPoints);
                LocationInfo locationInfo = MockDataGenerator.generateLocationInfo(chargingPoints);
                List<PointOfInterest> pois = MockDataGenerator.generatePointsOfInterest(poiCount);
                pois.forEach(poi -> poi.setLocationInfo(locationInfo)); // Set the relationship
                locationInfo.setPointsOfInterest(pois);
                locationInfoRepository.save(locationInfo);
            });

            // Now pass these tariffs to generate charging sessions
            List<ChargingSession> generatedSessions = MockDataGenerator.generateChargingSessions(sessionCount, generatedTariffs,allChargingPoints);
            chargingSessionRepository.saveAll(generatedSessions);



        };

    }


}
