package com.github.daveb8772.cms.cmsrestservice.repository;


import com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels.CommandRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandRequestRepository extends JpaRepository<CommandRequest, String> {
    // Custom query methods can be defined here if needed
}