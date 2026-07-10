package com.example.demo2.service;

import com.example.demo2.exception.UserNotFoundException;
import com.example.demo2.model.User;
import com.example.demo2.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static final Logger logger =
            LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepo userRepo;

    // ==========================================
    // Added for Spring Security
    // Used to encrypt passwords before saving
    // ==========================================
    @Autowired
    private PasswordEncoder passwordEncoder;

    // ==========================================
    // Save User
    // ==========================================
    public User saveUser(User user) {

        logger.info("Saving user");

        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set default role if role is not provided
        if (user.getRole() == null || user.getRole().isBlank()) {
            user.setRole("ROLE_USER");
        }

        return userRepo.save(user);
    }

    // ==========================================
    // Get All Users
    // ==========================================
    public List<User> getAllUsers() {

        logger.info("Fetching all users");

        return userRepo.findAll();
    }

    // ==========================================
    // Get User By Id
    // ==========================================
    public User getUserById(Long id) {

        logger.info("Fetching user with id {}", id);

        return userRepo.findById(id)

                .orElseThrow(() -> {

                    logger.error("User not found with id {}", id);

                    return new UserNotFoundException(
                            "User not found with id : " + id);

                });
    }

    // ==========================================
    // Update User
    // ==========================================
    public User updateUser(Long id, User user) {

        User existing = userRepo.findById(id)

                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id : " + id));

        existing.setName(user.getName());
        existing.setEmail(user.getEmail());

        // Encrypt updated password
        if (user.getPassword() != null &&
                !user.getPassword().isBlank()) {

            existing.setPassword(
                    passwordEncoder.encode(user.getPassword()));
        }

        if (user.getRole() != null &&
                !user.getRole().isBlank()) {

            existing.setRole(user.getRole());
        }

        return userRepo.save(existing);
    }

    // ==========================================
    // Delete User
    // ==========================================
    public String deleteUser(Long id) {

        User user = userRepo.findById(id)

                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id : " + id));

        userRepo.delete(user);

        return "User Deleted Successfully";
    }

}