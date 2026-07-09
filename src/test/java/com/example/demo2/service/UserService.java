package com.example.demo2.service;

import com.example.demo2.exception.UserNotFoundException;
import com.example.demo2.model.User;
import com.example.demo2.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserService userService;

    @Test
    void testSaveUser() {

        User user = new User("Nadeem", "nadeem@gmail.com");

        when(userRepo.save(user)).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertNotNull(savedUser);
        assertEquals("Nadeem", savedUser.getName());
        assertEquals("nadeem@gmail.com", savedUser.getEmail());

        verify(userRepo, times(1)).save(user);
    }

    @Test
    void testGetAllUsers() {

        User user1 = new User("Nadeem", "nadeem@gmail.com");
        User user2 = new User("Rahul", "rahul@gmail.com");

        when(userRepo.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<User> users = userService.getAllUsers();

        assertEquals(2, users.size());

        verify(userRepo, times(1)).findAll();
    }

    @Test
    void testGetUserById() {

        User user = new User("Nadeem", "nadeem@gmail.com");
        user.setId(1L);

        when(userRepo.findById(1L)).thenReturn(Optional.of(user));

        User foundUser = userService.getUserById(1L);

        assertNotNull(foundUser);
        assertEquals(1L, foundUser.getId());

        verify(userRepo, times(1)).findById(1L);
    }

    @Test
    void testGetUserByIdThrowsException() {

        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.getUserById(1L));

        verify(userRepo, times(1)).findById(1L);
    }

    @Test
    void testUpdateUser() {

        User existingUser = new User("Old Name", "old@gmail.com");
        existingUser.setId(1L);

        User updatedUser = new User("New Name", "new@gmail.com");

        when(userRepo.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepo.save(any(User.class))).thenReturn(existingUser);

        User result = userService.updateUser(1L, updatedUser);

        assertEquals("New Name", result.getName());
        assertEquals("new@gmail.com", result.getEmail());

        verify(userRepo).findById(1L);
        verify(userRepo).save(existingUser);
    }

    @Test
    void testUpdateUserThrowsException() {

        User updatedUser = new User("New Name", "new@gmail.com");

        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.updateUser(1L, updatedUser));

        verify(userRepo).findById(1L);
        verify(userRepo, never()).save(any());
    }

    @Test
    void testDeleteUser() {

        User user = new User("Nadeem", "nadeem@gmail.com");
        user.setId(1L);

        when(userRepo.findById(1L)).thenReturn(Optional.of(user));

        String message = userService.deleteUser(1L);

        assertEquals("User delete Succesffully", message);

        verify(userRepo).findById(1L);
        verify(userRepo).deleteById(1L);
    }

    @Test
    void testDeleteUserThrowsException() {

        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.deleteUser(1L));

        verify(userRepo).findById(1L);
        verify(userRepo, never()).deleteById(anyLong());
    }

}