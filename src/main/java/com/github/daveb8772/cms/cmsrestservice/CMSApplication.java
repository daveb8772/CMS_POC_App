package com.github.daveb8772.cms.cmsrestservice;

import com.github.daveb8772.cms.cmsrestservice.Security.SecurityConfiguration;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Arrays;

@SpringBootApplication
@EnableJpaRepositories("com.github.daveb8772.cms.cmsrestservice.repository")
public class CMSApplication {
	private static final Logger logger = LoggerFactory.getLogger(CMSApplication.class);
	public static void main(String[] args) {
		logger.info("************** Initializing SpringApplication **************");
		logger.info("************** Initializing SpringApplication **************");
		SpringApplication.run(CMSApplication.class, args);
	}

	@Autowired
	private Environment environment;

	@PostConstruct
	public void logProfileAndDbInfo() {
		// Log active profiles
		String[] activeProfiles = environment.getActiveProfiles();
		logger.info("Active Profiles: {}", Arrays.toString(activeProfiles));

		// Log the database URL
		String databaseUrl = environment.getProperty("spring.datasource.url");
		logger.info("Database URL: {}", databaseUrl);

		// Log application version from the properties
		String appVersion = environment.getProperty("info.app.version");
		logger.info("Application Version: {}", appVersion);
	}

}
