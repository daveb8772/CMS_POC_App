package com.github.daveb8772.ocpi.ocpirestservice.repository;

import com.github.daveb8772.ocpi.ocpirestservice.controller.Models.ChargeModels.ChargingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargingSessionRepository extends JpaRepository<ChargingSession, Long> {
    // Custom query methods can be defined here if needed
}