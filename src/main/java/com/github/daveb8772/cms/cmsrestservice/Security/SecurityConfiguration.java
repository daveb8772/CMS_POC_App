package com.github.daveb8772.cms.cmsrestservice.Security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;





@Configuration
public class SecurityConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        logger.info("Initializing Security Filter Chain");
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/actuator/prometheus").permitAll() // Allow unauthenticated access to Prometheus endpoint
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults()) // Updated configuration style
                .csrf(CsrfConfigurer::disable);// Optional: Disable CSRF for testing

        return http.build();
    }
}
