package com.lifeos.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

/**
 * Enterprise Application Properties binding configuration prefixed with "app".
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app")
public class ApplicationProperties {

    private String name = "LifeOS";
    private String version = "1.0.0";
    private String description = "AI-First Personal Operating System Backend";

    @NestedConfigurationProperty
    private ApiProperties api = new ApiProperties();

    @NestedConfigurationProperty
    private JwtProperties jwt = new JwtProperties();

    @NestedConfigurationProperty
    private SecurityProperties security = new SecurityProperties();

    @NestedConfigurationProperty
    private CacheProperties cache = new CacheProperties();

    @Getter
    @Setter
    public static class ApiProperties {
        private String version = "v1";
        private String basePath = "/api/v1";
    }

    @Getter
    @Setter
    public static class CacheProperties {
        private RedisCacheProperties redis = new RedisCacheProperties();

        @Getter
        @Setter
        public static class RedisCacheProperties {
            private long defaultTtlSeconds = 3600;
        }
    }
}
