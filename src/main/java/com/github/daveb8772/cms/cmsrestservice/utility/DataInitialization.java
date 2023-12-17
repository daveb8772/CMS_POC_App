package com.github.daveb8772.cms.cmsrestservice.utility;

import com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels.ChargingPoint;
import com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels.CommandRequest;
import com.github.daveb8772.cms.cmsrestservice.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Configuration
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
        this.chargingSessionRepository = chargingSessionRepository;
        this.authorizationRepository = authorizationRepository;
        this.tariffRepository = tariffRepository;
        this.chargingPointRepository = chargingPointRepository;
        this.commandRequestRepository = commandRequestRepository;
        this.locationInfoRepository = locationInfoRepository;
    }

    @Bean
    @Transactional
    CommandLineRunner initDatabase() {

        return args -> {
            // Generate mock data using MockDataGenerator and save it using the repositories
            chargingSessionRepository.saveAll(MockDataGenerator.generateChargingSessions(10));
            authorizationRepository.save(MockDataGenerator.generateAuthorization(true));
            tariffRepository.saveAll(MockDataGenerator.generateTariffs(5));
            locationInfoRepository.save(MockDataGenerator.generateLocationInfo());
               // Generate and save ChargingPoint entities with commands
                 List<ChargingPoint> chargingPoints = MockDataGenerator.generateChargingPoints(5);
                chargingPoints.forEach(chargingPoint -> {
                List<CommandRequest> commandRequests = MockDataGenerator.generateCommandRequests(3, chargingPoint);
                chargingPoint.setCommandRequests(commandRequests);});
            chargingPointRepository.saveAll(chargingPoints);
            };

    }


}
