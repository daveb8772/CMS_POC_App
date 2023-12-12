package com.github.daveb8772.ocpi.ocpirestservice.repository;


import com.github.daveb8772.ocpi.ocpirestservice.controller.Models.DepotModels.ChargingPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargingPointRepository extends JpaRepository<ChargingPoint, String> {

    // Custom query methods can be defined here if needed
}