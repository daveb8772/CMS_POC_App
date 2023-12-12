package com.github.daveb8772.ocpi.ocpirestservice.repository;

import com.github.daveb8772.ocpi.ocpirestservice.controller.ResponseModels.AuthorizationResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizationRepository extends JpaRepository<AuthorizationResponse, String> {

    // Custom query methods can be defined here if needed
}