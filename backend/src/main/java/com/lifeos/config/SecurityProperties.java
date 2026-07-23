package com.lifeos.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Security Properties configuration bound to "app.security".
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app.security")
public class SecurityProperties {

    private List<String> permittedPaths = new ArrayList<>(List.of(
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/actuator/**",
            "/api/v1/auth/**",
            "/error"
    ));

    @NestedConfigurationProperty
    private CorsProperties cors = new CorsProperties();

    @Getter
    @Setter
    public static class CorsProperties {
        private List<String> allowedOrigins = List.of("http://localhost:3000", "http://localhost:5173");
        private List<String> allowedMethods = List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS");
        private List<String> allowedHeaders = List.of("*");
        private List<String> exposedHeaders = List.of("Authorization", "X-Correlation-ID", "X-Total-Count");
        private boolean allowCredentials = true;
        private long maxAgeSeconds = 3600;
    }
}
