package com.yugal.jwt_pgsql.auth;

import com.yugal.jwt_pgsql.config.JwtService;
import com.yugal.jwt_pgsql.user.Role;
import com.yugal.jwt_pgsql.user.User;
import com.yugal.jwt_pgsql.user.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        // Create a new user
        System.out.println("Inside register service ---->> ");
        User user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword())) // Encode the password
                .role(Role.USER) // Set default role
                .build();

        userRepository.save(user); // Save the user in the repository

        // Generate JWT token
        String jwtToken = jwtService.generateToken(user);
        System.out.println("Inside register service, finished ---->> ");

        return new AuthenticationResponse(jwtToken); // Return the token in response
    }

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        
        // Authenticate the user using email and password
        authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()) );
        

        // Fetch user from the repository
        User user = userRepository
                .findByEmail(authenticationRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Fetch roles from the user
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        // Create a token with roles/authorities included
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getEmail(), null, authorities);

        // Set the authentication token in the SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // Generate JWT token
        String jwtToken = jwtService.generateToken(user);
        System.out.println("Inside login service, token generated ---->> ");

        // Return the JWT token in the response
        return new AuthenticationResponse(jwtToken);
    }

}
