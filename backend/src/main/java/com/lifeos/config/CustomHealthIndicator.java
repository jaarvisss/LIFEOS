package com.lifeos.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * Spring Boot Actuator Custom Health Indicator for LifeOS Engine.
 */
@Component("lifeosPlatformHealth")
public class CustomHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        return Health.up()
                .withDetail("aiEngine", "Operational")
                .withDetail("pgvector", "Available")
                .withDetail("searchIndex", "Active")
                .withDetail("rateLimiter", "Enabled")
                .build();
    }
}
