package com.lifeos.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Enterprise Springdoc OpenAPI 3.0 Configuration supporting JWT Bearer authentication scheme and Swagger UI documentation.
 */
@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI customOpenAPI(ApplicationProperties appProperties) {
        return new OpenAPI()
                .info(new Info()
                        .title(appProperties.getName() + " Backend API Platform")
                        .version(appProperties.getVersion())
                        .description(appProperties.getDescription())
                        .contact(new Contact()
                                .name("LifeOS Core Platform Team")
                                .email("architecture@lifeos.io")
                                .url("https://lifeos.io"))
                        .license(new License()
                                .name("Enterprise Proprietary License")
                                .url("https://lifeos.io/license")))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .name(SECURITY_SCHEME_NAME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Provide valid Bearer JWT access token to authenticate API calls.")));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("lifeos-public-api")
                .pathsToMatch("/api/v1/**")
                .build();
    }

    @Bean
    public GroupedOpenApi actuatorApi() {
        return GroupedOpenApi.builder()
                .group("lifeos-actuator-api")
                .pathsToMatch("/actuator/**")
                .build();
    }
}
