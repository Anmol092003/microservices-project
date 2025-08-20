package com.yugal.jwt_pgsql.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@Component
@RequiredArgsConstructor
// Bean to validate token
// This filter checks the JWT token in the request header and sets the authentication in the security context if valid
// This is similar to middleware in Express.js
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService; // Service for handling JWT operations (e.g., extraction, validation)
    private final UserDetailsService userDetailsService; // Service for loading user details from the DB

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String path = request.getRequestURI();
        final String auth = request.getHeader("Authorization"); // Retrieve the 'Authorization' header from the incoming request

        // Check if the 'Authorization' header exists and starts with 'Bearer '
        if (auth == null || !auth.startsWith("Bearer ")) {
            // Only allow it through if it's a public path
            if (path.startsWith("/api/v1/auth/")) {
                filterChain.doFilter(request, response); // It is like calling next() in Express.js middleware
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Missing or invalid token");
            }

            return;
        }


        final String jwtToken;
        final String userEmail;

        // Extract the JWT token (after 'Bearer ' prefix)
        jwtToken = auth.substring(7);
        // Extract the username (email) from the JWT token
        System.out.println("Inside JwtAuthFilter, extract username called ---->> ");
        userEmail = jwtService.extractUserName(jwtToken);
        System.out.println("Inside JwtAuthFilter, extract username call finished ---->> ");

        // If the email is found in the token and the user is not already authenticated
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println("Inside jwtTOKEN and SecurityContext didn't matches ---->> ");

            // Load user details from the database using the extracted email from the token
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // Validate the JWT token to ensure it matches the user's email and not expired
            if (jwtService.isTokenValid(jwtToken, userDetails.getUsername())) {
                // Create an authentication token with user details
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());

                // Set the details of the request (e.g., remote address) in the authentication object
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication token in the security context (user is now authenticated)
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                // If token is invalid, clear context and respond with 401
                SecurityContextHolder.clearContext();

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid or expired token.");
                
                return;
            }
        } else {
            // Check if the JWT token in the request matches the user in the SecurityContext
            System.out.println("Inside jwtTOKEN and SecurityContext matches ---->> ");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.getPrincipal().equals(userEmail)) {
                // JWT and UsernamePasswordAuthenticationToken match, so proceed without changes
                // Optionally, you can re-validate the token here if needed
            } else {
                // JWT does not match with SecurityContext's Authentication
                SecurityContextHolder.clearContext();

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);// Clear the SecurityContext and force the user to re-authenticate
                response.getWriter().write("Token does not match with authenticated user.");

                return;
            }
        }

        // Pass the request and response to the next filter in the Spring Security filter chain
        // It is like calling next() in Express.js middleware
        filterChain.doFilter(request, response);
    }
}
