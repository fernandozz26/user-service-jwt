package com.store_user_service.userservice.service;

import java.util.Optional;

import com.store_user_service.userservice.entities.Rol;
import com.store_user_service.userservice.enums.RolName;
import com.store_user_service.userservice.repository.RolRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class RolService {

    @Autowired
    RolRepository rolRepository;

    public Optional<Rol> getByRolName (RolName rolName){
        return rolRepository.findByRolName(rolName);
    }

}
