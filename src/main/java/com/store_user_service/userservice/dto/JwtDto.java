package com.store_user_service.userservice.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtDto {
    private String token;
    private final String bearer  = "Bearer";
    private String username;
    private Collection< ? extends GrantedAuthority> authorities;

}
