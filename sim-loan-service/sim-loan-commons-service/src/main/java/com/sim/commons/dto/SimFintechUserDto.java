package com.sim.commons.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sim.commons.enumeration.Gender;
import com.sim.commons.enumeration.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public record SimFintechUserDto(
        UUID id, String firstName, String lastName,  Gender gender, String password,
        String email, String mobile, @JsonIgnore String secretKey,@JsonIgnore UUID publicId, Role role, LocalDate dateOfBirth
) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
