package com.store_user_service.userservice.repository;

import java.util.Optional;

import com.store_user_service.userservice.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM user where username = :username")
    public User findByusername(@Param("username") String username);

    @Query(nativeQuery = true, value = "SELECT * FROM user where email = :email")
    public User findByEmail(@Param("email") String email);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String emeal);
}
