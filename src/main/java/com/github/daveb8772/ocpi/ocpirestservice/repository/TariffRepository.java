package com.github.daveb8772.ocpi.ocpirestservice.repository;


import com.github.daveb8772.ocpi.ocpirestservice.controller.Models.DepotModels.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TariffRepository extends JpaRepository<Tariff, String> {
    // Custom query methods can be defined here if needed
}