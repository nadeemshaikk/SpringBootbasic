package com.example.demo2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 3, max = 30, message = "Name should conatin 3 to 30 characters")
    private String name;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "enter valid email")
    @Column(unique = true)

    private String email;

    // ===== Added for Spring Security =====
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, message = "Password should be at least 6 characters")
    private String password;

    // ===== Added for Spring Security =====
    private String role = "ROLE_USER";

    public User() {
    }

    public User(Long id, String name, String email, String password, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}