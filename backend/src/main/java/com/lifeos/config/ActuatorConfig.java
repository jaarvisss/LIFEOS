package com.lifeos.config;

import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * Enterprise Spring Boot Actuator Customization.
 */
@Configuration
public class ActuatorConfig {

    @Bean
    public InfoContributor customInfoContributor(ApplicationProperties appProperties) {
        return builder -> builder
                .withDetail("app", Map.of(
                        "name", appProperties.getName(),
                        "version", appProperties.getVersion(),
                        "description", appProperties.getDescription(),
                        "platform", "Java 21 / Spring Boot 3.3+ / PostgreSQL 16+ / Redis"
                ));
    }
}
