package com.yugal.jwt_pgsql.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:jwt.properties")
public class JwtSecret {

    @Value("${jwt.secret}")
    private String secret;

    // Getters
    public String getSecret() {
        return secret;
    }
}

