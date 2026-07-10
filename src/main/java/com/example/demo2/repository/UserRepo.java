package com.example.demo2.repository;

import com.example.demo2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    // ===========================
    // Added for Spring Security
    // Used during login to find user by email
    // ===========================
    Optional<User> findByEmail(String email);


}
