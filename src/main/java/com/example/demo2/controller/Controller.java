package com.example.demo2.controller;

import com.example.demo2.model.User;
import com.example.demo2.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class Controller {

    @Autowired
    private UserService userService;

    // Registration API
    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        return userService.saveUser(user);
    }

    // Protected API
    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    // Protected API
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // Protected API
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id,
                           @Valid @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    // Protected API
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}