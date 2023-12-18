package com.github.daveb8772.cms.cmsrestservice;

import com.github.daveb8772.cms.cmsrestservice.Security.SecurityConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.github.daveb8772.cms.cmsrestservice.repository")
public class CMSApplication {
	private static final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);
	public static void main(String[] args)
	{
		logger.info("************** Initializing SpringApplication **************");
		SpringApplication.run(CMSApplication.class, args);
	}
}
