package com.store_user_service.userservice.entities;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MainUser implements UserDetails {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private Date birthday;
    // es propio de spring security
    private Collection<? extends GrantedAuthority> authorities;
    
    public static MainUser build(User user){
        List<GrantedAuthority> authorities = user.getRols()
        .stream().map(rol -> new SimpleGrantedAuthority(rol.getRolName().name()))
        .collect(Collectors.toList());
        return new MainUser(user.getFirstName(), user.getLastName(),user.getUsername() ,user.getEmail(), user.getPassword(),user.getBirthday() ,authorities);
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }
    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }
}
