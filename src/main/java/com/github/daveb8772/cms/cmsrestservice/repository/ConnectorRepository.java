package com.github.daveb8772.cms.cmsrestservice.repository;

import com.github.daveb8772.cms.cmsrestservice.controller.Models.EntityModels.Connector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface ConnectorRepository extends JpaRepository<Connector, Long> {

}

