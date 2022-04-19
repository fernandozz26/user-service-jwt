package com.store_user_service.userservice.service;

import java.util.Optional;

import javax.transaction.Transactional;

import com.store_user_service.userservice.entities.User;
import com.store_user_service.userservice.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {
    @Autowired
    UserRepository userRepository;

    public Optional<User> getByUsername(String username){
        
        return userRepository.findByUsername(username);
    }

    public User getByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public void save(User user){
        userRepository.save(user);
    }
}
