package com.github.daveb8772.cms.cmsrestservice.utility;

import com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels.*;
import com.github.daveb8772.cms.cmsrestservice.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

@Configuration
@Profile("dev")
public class DataInitialization {

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
            // First, generate and save tariffs
            List<Tariff> generatedTariffs = MockDataGenerator.generateTariffs(5);
            tariffRepository.saveAll(generatedTariffs);

            // Now pass these tariffs to generate charging sessions
            List<ChargingSession> generatedSessions = MockDataGenerator.generateChargingSessions(10, generatedTariffs);
            chargingSessionRepository.saveAll(generatedSessions);



            // Generate and save ChargingPoint entities with commands
            // Loop to create 3 locations
            // Generate and save 5 ChargingPoint entities with commands for each location
            // Save LocationInfo with the associated ChargingPoints
            IntStream.range(0, 3).mapToObj(i -> MockDataGenerator.generateChargingPoints(5)).forEach(chargingPoints -> {
                chargingPoints.forEach(chargingPoint -> {
                    List<CommandRequest> commandRequests = MockDataGenerator.generateCommandRequests(3, chargingPoint);
                    chargingPoint.setCommandRequests(commandRequests);
                });
                chargingPointRepository.saveAll(chargingPoints);
                LocationInfo locationInfo = MockDataGenerator.generateLocationInfo(chargingPoints);
                locationInfoRepository.save(locationInfo);
            });

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

        };

    }


}
