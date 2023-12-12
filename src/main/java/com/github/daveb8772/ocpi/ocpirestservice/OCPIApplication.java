package com.github.daveb8772.ocpi.ocpirestservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.github.daveb8772.ocpi.ocpirestservice.repository")
public class OCPIApplication {

	public static void main(String[] args) {
		SpringApplication.run(OCPIApplication.class, args);
	}
}
