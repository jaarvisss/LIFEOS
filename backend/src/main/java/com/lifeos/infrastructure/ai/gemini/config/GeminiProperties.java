package com.lifeos.infrastructure.ai.gemini.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties for Google Gemini AI Gateway.
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "app.gemini")
public class GeminiProperties {

    private String apiKey = "mock-gemini-key-for-test-environment";
    private String modelId = "gemini-1.5-flash";
    private String baseUrl = "https://generativelanguage.googleapis.com/v1beta";
    private int timeoutSeconds = 30;
    private int maxRetries = 3;
}
