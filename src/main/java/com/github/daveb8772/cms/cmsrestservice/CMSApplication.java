package com.github.daveb8772.cms.cmsrestservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.github.daveb8772.cms.cmsrestservice.repository")
public class CMSApplication {

	public static void main(String[] args) {
		SpringApplication.run(CMSApplication.class, args);
	}
}
