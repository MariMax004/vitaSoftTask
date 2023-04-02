package com.example.vitasofttask.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;



@Data
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix="jwt")
public class JwtConfig {
    private String jwtSecret;
    private Long timeOfActionAuthToken;
    private Long timeOfActionAccessToken;
}
