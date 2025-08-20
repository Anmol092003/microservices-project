package com.yugal.jwt_pgsql.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.authentication.AuthenticationProvider;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    //This will check for which routes the Role check should be applied (RBAC)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF is not required for REST and Stateless APIs
                .authorizeHttpRequests(req -> 
                     req.requestMatchers("/api/v1/auth/**").permitAll()  // Public endpoints for authentication
                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN") // Restrict to ADMIN role
                        .requestMatchers("/api/v1/user/**").hasAnyRole("ADMIN", "USER") // Allow both ADMIN and USER roles
                        .anyRequest() // no role check required for other requests
                        .authenticated() // All other requests must be authenticated
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless session management
                )
                .authenticationProvider(authenticationProvider) // Register authentication provider in Spring for username & paswword check
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter before authentication filter

        return http.build();
    }

}
