package com.github.daveb8772.cms.cmsrestservice.repository;

import com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository  extends JpaRepository<UserCredentials, Long> {

    // Custom query method
    Optional<UserCredentials> findByName(String name);
}

