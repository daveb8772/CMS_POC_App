package com.github.daveb8772.cms.cmsrestservice.repository;


import com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels.ChargingPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargingPointRepository extends JpaRepository<ChargingPoint, String> {

    // Custom query methods can be defined here if needed
}