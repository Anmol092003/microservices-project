package com.yugal.jwt_pgsql.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role; // Enum for role (ROLE_ADMIN, ROLE_USER)

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Ensure the role is prefixed with "ROLE_" for Spring Security compatibility
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return email; // Use the email as the username
    }

    @Override
    public String getPassword() {
        return password; // Return the user's password
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Account is never expired in this case
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Account is never locked in this case
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Credentials are never expired in this case
    }

    @Override
    public boolean isEnabled() {
        return true; // Account is always enabled
    }
}
