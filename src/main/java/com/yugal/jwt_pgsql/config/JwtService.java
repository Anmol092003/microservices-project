package com.yugal.jwt_pgsql.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private final String SECRET_KEY; 
    private final int EXPIRATION_TIME_IN_MS = 1000 * 60 * 60;

    // Constructor-based Dependency Injection
    @Autowired
    public JwtService(JwtSecret jwtSecret) {
        this.SECRET_KEY = jwtSecret.getSecret();
    }

    // Generate a signing key from the base64 encoded secret
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    // <-------- TOKEN GENERATION METHODS -------->
    // Generate a JWT Token with user details
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    // Generate a JWT Token with additional claims and user details
    @SuppressWarnings("deprecation")
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername()) // Set the subject (username) i.e., email
                .setIssuedAt(new Date()) // Set the issued date to current time
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_IN_MS)) // Set expiration date
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) // Sign the JWT with the secret key
                .compact(); // Build and return the JWT token
    }



    // <-------- CLAIMS -------->
    // Extract claims/information from the JWT token
    @SuppressWarnings("deprecation")
    private Claims extractAllClaims(String jwtToken) {
        try {
            return Jwts.parser()
                    .setSigningKey(getSignInKey()) // Use the signing key to validate the token
                    .build()
                    .parseClaimsJws(jwtToken) // Parse the JWT token
                    .getBody();
        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }

    // Extract a specific claim/information from the JWT Token using a function
    private <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(jwtToken); // Extract claims from the token
        
        return claimsResolver.apply(claims); // Use the function to extract the required claim
    }



    // <-------- TOKEN EXTRACTION METHODS -------->
    // Extract username from JWT token
    public String extractUserName(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject); // Extract the subject (username)
    }

    // Extract role from JWT token if it was set in extraClaims hashMap
    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    // <-------- TOKEN EXPIRATION VALIDATION -------->
    private Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration); // Extract the expiration date
    }

    // Validate if the token has expired
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date()); // Check if the expiration date is before current Date & time
    }

    // Validate if the token is still valid (username matches and token is not expired)
    public boolean isTokenValid(String token, String userEmail) {
        return (userEmail.equals(extractUserName(token)) && !isTokenExpired(token));
    }
}
