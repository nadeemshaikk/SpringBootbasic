package com.example.demo2.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testDefaultConstructor() {

        User user = new User();

        assertNotNull(user);
    }

    @Test
    void testParameterizedConstructor() {

        User user = new User("Nadeem", "nadeem@gmail.com");

        assertEquals("Nadeem", user.getName());
        assertEquals("nadeem@gmail.com", user.getEmail());
    }

    @Test
    void testSettersAndGetters() {

        User user = new User();

        user.setId(1L);
        user.setName("Rahul");
        user.setEmail("rahul@gmail.com");

        assertEquals(1L, user.getId());
        assertEquals("Rahul", user.getName());
        assertEquals("rahul@gmail.com", user.getEmail());
    }

    @Test
    void testUserObjectIsNotNull() {

        User user = new User();

        assertNotNull(user);
    }

    @Test
    void testUserNameIsNotNull() {

        User user = new User("Nadeem", "nadeem@gmail.com");

        assertNotNull(user.getName());
    }

    @Test
    void testUserEmailIsNotNull() {

        User user = new User("Nadeem", "nadeem@gmail.com");

        assertNotNull(user.getEmail());
    }

    @Test
    void testNameShouldBeEqual() {

        User user = new User("Nadeem", "nadeem@gmail.com");

        assertEquals("Nadeem", user.getName());
    }

    @Test
    void testEmailShouldBeEqual() {

        User user = new User("Nadeem", "nadeem@gmail.com");

        assertEquals("nadeem@gmail.com", user.getEmail());
    }

    @Test
    void testNameShouldNotBeEqual() {

        User user = new User("Nadeem", "nadeem@gmail.com");

        assertNotEquals("Rahul", user.getName());
    }

    @Test
    void testEmailContainsAtSymbol() {

        User user = new User("Nadeem", "nadeem@gmail.com");

        assertTrue(user.getEmail().contains("@"));
    }

    @Test
    void testEmailEndsWithCom() {

        User user = new User("Nadeem", "nadeem@gmail.com");

        assertTrue(user.getEmail().endsWith(".com"));
    }

    @Test
    void testInvalidEmail() {

        User user = new User("Nadeem", "abcgmail.com");

        assertFalse(user.getEmail().contains("@"));
    }
}