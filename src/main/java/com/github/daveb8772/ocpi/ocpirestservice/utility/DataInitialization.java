package com.github.daveb8772.ocpi.ocpirestservice.utility;

import com.github.daveb8772.ocpi.ocpirestservice.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class DataInitialization {
       /* TODO
    private final ChargingSessionRepository chargingSessionRepository;
    private final ChargingPointRepository chargingPointRepository;
    private final CommandRepository userRepository;
    private final AuthorizationRepository authorizationRepository;
    private final TariffRepository tariffRepository;
    private final LocationInfoRepository locationInfoRepository;

    public DataInitialization(ChargingSessionRepository chargingSessionRepository,
                              ChargingPointRepository chargingPointRepository,
                              CommandRepository userRepository,
                              AuthorizationRepository authorizationRepository,
                              TariffRepository tariffRepository,
                              LocationInfoRepository locationInfoRepository) {
        this.chargingSessionRepository = chargingSessionRepository;
        this.chargingPointRepository = chargingPointRepository;
        this.userRepository = userRepository;
        this.authorizationRepository = authorizationRepository;
        this.tariffRepository = tariffRepository;
        this.locationInfoRepository = locationInfoRepository;
    }

    @Bean
    @Transactional
    CommandLineRunner initDatabase() {

        return args -> {
            // Generate mock data using MockDataGenerator and save it using the repositories
            chargingSessionRepository.saveAll(MockDataGenerator.generateChargingSessions(10));

            userRepository.save(MockDataGenerator.generateUserInfo("user1"));
            authorizationRepository.save(MockDataGenerator.generateAuthorizationResponse(true));
            tariffRepository.saveAll(MockDataGenerator.generateTariffs(5));
            locationInfoRepository.save(MockDataGenerator.generateLocationInfo());
               // Generate and save ChargingPoint entities with commands
                 List<ChargingPoint> chargingPoints = MockDataGenerator.generateChargingPoints(5);
                chargingPoints.forEach(chargingPoint -> {
                List<CommandRequest> commandRequests = MockDataGenerator.generateCommandRequests(3, chargingPoint);
                chargingPoint.setCommandRequests(commandRequests);
            });
            chargingPointRepository.saveAll(chargingPoints);
            };


        return null;
    }

        */
}
