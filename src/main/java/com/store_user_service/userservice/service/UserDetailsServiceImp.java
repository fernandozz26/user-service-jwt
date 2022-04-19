package com.store_user_service.userservice.service;

import org.springframework.stereotype.Service;

import com.store_user_service.userservice.entities.MainUser;
import com.store_user_service.userservice.entities.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class UserDetailsServiceImp implements UserDetailsService{

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByUsername(username).get();
        
        return MainUser.build(user);
    }
    
}
