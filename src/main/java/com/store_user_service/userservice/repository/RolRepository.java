package com.store_user_service.userservice.repository;

import java.util.Optional;

import com.store_user_service.userservice.entities.Rol;
import com.store_user_service.userservice.enums.RolName;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    
    public Optional<Rol> findByRolName(RolName rolName);
}
