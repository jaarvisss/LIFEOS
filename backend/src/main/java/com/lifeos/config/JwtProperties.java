package com.lifeos.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Enterprise JWT properties configuration bound to "app.jwt".
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app.jwt")
public class JwtProperties {

    private String secretKey = "dGVzdF9zZWNyZXRfa2V5X2Zvcl9saWZlb3NfYmFja2VuZF9lbnRlcnByaXNlX3NlY3VyaXR5X2ZyYW1ld29ya18yMDI2X2p3dF90b2tlbl9zcmluZ19ib290XzM1";
    private long expirationMs = 86400000; // 24 Hours
    private long refreshExpirationMs = 604800000; // 7 Days
    private String issuer = "LifeOS-Auth-Service";
    private String audience = "LifeOS-Clients";
    private String tokenPrefix = "Bearer ";
    private String headerName = "Authorization";
}
