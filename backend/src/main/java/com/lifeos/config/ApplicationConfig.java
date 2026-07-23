package com.lifeos.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main Enterprise Application Configuration for LifeOS enabling Async and Scheduling features.
 */
@Configuration
@EnableAsync
@EnableScheduling
public class ApplicationConfig {
}
