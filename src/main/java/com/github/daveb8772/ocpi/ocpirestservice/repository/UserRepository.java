package com.github.daveb8772.ocpi.ocpirestservice.repository;


import com.github.daveb8772.ocpi.ocpirestservice.controller.ResponseModels.CommandResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<CommandResponse, String> {
    // Custom query methods can be defined here if needed
}