package com.github.daveb8772.cms.cmsrestservice.utility;

import com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels.*;
import com.github.daveb8772.cms.cmsrestservice.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Configuration
@Profile("dev")
public class DataInitialization {

    private final ChargingSessionRepository chargingSessionRepository;
    private final ChargingPointRepository chargingPointRepository;
    private final CommandRequestRepository commandRequestRepository;
    private final AuthorizationRepository authorizationRepository;
    private final TariffRepository tariffRepository;
    private final LocationInfoRepository locationInfoRepository;

    public DataInitialization(ChargingSessionRepository chargingSessionRepository,
                              ChargingPointRepository chargingPointRepository,
                              CommandRequestRepository commandRequestRepository,
                              AuthorizationRepository authorizationRepository,
                              TariffRepository tariffRepository,
                              LocationInfoRepository locationInfoRepository) {
        // Save references to repositories...
        this.chargingSessionRepository = chargingSessionRepository;
        this.authorizationRepository = authorizationRepository;
        this.tariffRepository = tariffRepository;
        this.chargingPointRepository = chargingPointRepository;
        this.commandRequestRepository = commandRequestRepository;
        this.locationInfoRepository = locationInfoRepository;
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
            authorizationRepository.save(MockDataGenerator.generateAuthorization(true));


            // Generate and save ChargingPoint entities with commands
            List<ChargingPoint> chargingPoints = MockDataGenerator.generateChargingPoints(5);
            chargingPoints.forEach(chargingPoint -> {
                List<CommandRequest> commandRequests = MockDataGenerator.generateCommandRequests(3, chargingPoint);
                chargingPoint.setCommandRequests(commandRequests);
            });
            chargingPointRepository.saveAll(chargingPoints);

            // Save LocationInfo with the associated ChargingPoints
            LocationInfo locationInfo = MockDataGenerator.generateLocationInfo(chargingPoints);
            locationInfoRepository.save(locationInfo);

        };

    }


}
