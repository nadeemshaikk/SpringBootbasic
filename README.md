
# Spring Security with Basic Authentication - Notes

## Project Overview

This project implements **Spring Security with Basic Authentication** using a **Spring Boot REST API**, **H2 Database**, and **BCrypt Password Encryption**.

The application authenticates users stored in the database instead of using Spring Security's default in-memory user.

---

# Technologies Used

* Java 21
* Spring Boot 3.5.x
* Spring Security
* Spring Data JPA
* H2 Database
* BCrypt Password Encoder
* Maven
* Postman

---

# Project Flow

```text
Client

↓

HTTP Request

↓

Spring Security Filter

↓

Authentication

↓

CustomUserDetailsService

↓

User Repository

↓

H2 Database

↓

UserDetails

↓

Password Verification

↓

Controller

↓

Service

↓

Database
```

---

# What is Spring Security?

Spring Security is a framework that provides security features for Spring applications.

It provides:

* Authentication
* Authorization
* Password Encryption
* Protection against common attacks (CSRF, Session Fixation, etc.)

---

# Authentication vs Authorization

## Authentication

Authentication verifies **who the user is**.

Example:

```text
Email : nadeem@gmail.com
Password : 123456
```

If credentials are correct:

```
Authenticated
```

---

## Authorization

Authorization checks **what the authenticated user is allowed to access**.

Example:

```
ROLE_ADMIN

↓

Can Delete Users
```

```
ROLE_USER

↓

Cannot Delete Users
```

---

# Project Structure

```text
demo2
│
├── config
│      └── SecurityConfig.java
│
├── controller
│      └── Controller.java
│
├── model
│      └── User.java
│
├── repository
│      └── UserRepo.java
│
├── service
│      ├── UserService.java
│      └── CustomUserDetailsService.java
│
├── exception
│      ├── ErrorResponse.java
│      ├── GlobalExceptionHandler.java
│      └── UserNotFoundException.java
│
└── resources
       └── application.properties
```

---

# Step 1 - Add Spring Security Dependency

Added:

```xml
spring-boot-starter-security
```

Purpose:

* Enables Spring Security.
* Protects endpoints by default.
* Adds authentication support.

---

# Step 2 - Update User Entity

Added two new fields.

```java
private String password;
```

Purpose:

Stores user password.

---

```java
private String role;
```

Purpose:

Stores user role.

Example:

```
ROLE_USER

ROLE_ADMIN
```

---

# Step 3 - Update Repository

Added:

```java
Optional<User> findByEmail(String email);
```

Purpose:

Spring Security authenticates users using email.

---

# Step 4 - Password Encryption

Created

```java
PasswordEncoder
```

Implementation

```java
BCryptPasswordEncoder
```

Purpose:

Encrypt passwords before saving.

Example

Input

```
123456
```

Stored

```
$2a$10$XJDSKJDK...
```

Passwords are never stored in plain text.

---

# Step 5 - UserService

Added

```java
passwordEncoder.encode()
```

Purpose:

Encrypt password before saving into database.

Flow

```
User

↓

PasswordEncoder

↓

Encrypted Password

↓

Database
```

---

# Step 6 - CustomUserDetailsService

Implemented

```java
UserDetailsService
```

Purpose:

Loads users from database during login.

Method

```java
loadUserByUsername()
```

Although the method name says username, we use email.

Flow

```
Spring Security

↓

loadUserByUsername()

↓

findByEmail()

↓

Database

↓

Return UserDetails
```

---

# Step 7 - SecurityConfig

Created

```java
SecurityConfig
```

Main responsibilities:

* Configure security rules
* Configure authentication
* Configure password encoder
* Configure authentication provider
* Enable Basic Authentication

---

# PasswordEncoder Bean

```java
@Bean
PasswordEncoder
```

Purpose

Creates BCrypt encoder.

---

# DaoAuthenticationProvider

Purpose

Connects

```
Spring Security

↓

CustomUserDetailsService

↓

Database
```

Without this provider, Spring uses the default in-memory user.

---

# AuthenticationManager

Purpose

Manages the authentication process.

Used later for JWT login.

---

# SecurityFilterChain

Configured:

```java
.csrf(csrf -> csrf.disable())
```

Purpose:

Disabled CSRF because this is a REST API.

---

Configured:

```java
.requestMatchers(HttpMethod.POST,"/users").permitAll()
```

Purpose:

Allows new users to register.

---

Configured:

```java
.requestMatchers("/users/**").authenticated()
```

Purpose:

All remaining user APIs require login.

---

Configured:

```java
.httpBasic()
```

Purpose:

Enables Basic Authentication.

---

# Basic Authentication

Postman

Authorization

```
Basic Auth
```

Username

```
nadeem@gmail.com
```

Password

```
123456
```

Postman automatically sends

```
Authorization: Basic Base64(email:password)
```

Spring Security validates these credentials against the database.

---

# Request Flow

```
Client

↓

Basic Auth

↓

Spring Security

↓

CustomUserDetailsService

↓

Repository

↓

Database

↓

UserDetails

↓

Password Match

↓

Controller

↓

Response
```

---

# API Testing

## Register User

```
POST /users
```

Public API

Purpose

Register a new user.

---

## Get All Users

```
GET /users
```

Protected

Requires Basic Authentication.

---

## Get User

```
GET /users/{id}
```

Protected

---

## Update User

```
PUT /users/{id}
```

Protected

---

## Delete User

```
DELETE /users/{id}
```

Protected

---

# H2 Database

Database URL

```
jdbc:h2:mem:userdb
```

Console

```
http://localhost:8080/h2-console
```

Verify stored users

```sql
SELECT * FROM USERS;
```

---

# Password Flow

```
Registration

↓

Password

↓

BCrypt

↓

Encrypted Password

↓

Database

----------------------------------

Login

↓

Entered Password

↓

CustomUserDetailsService

↓

Database

↓

BCrypt matches()

↓

Authenticated
```

---

# Classes Summary

| Class                     | Purpose                                          |
| ------------------------- | ------------------------------------------------ |
| User                      | Entity representing a user                       |
| UserRepo                  | Accesses user data from the database             |
| UserService               | Business logic and password encryption           |
| Controller                | Exposes REST APIs                                |
| SecurityConfig            | Configures Spring Security                       |
| CustomUserDetailsService  | Loads users from the database for authentication |
| PasswordEncoder           | Encrypts passwords using BCrypt                  |
| DaoAuthenticationProvider | Connects Spring Security with the database       |
| AuthenticationManager     | Manages authentication                           |
| GlobalExceptionHandler    | Handles exceptions globally                      |
| ErrorResponse             | Standard error response object                   |

---

# Important Spring Security Concepts

### Authentication

Verifies user identity.

Example

```
Email

Password
```

---

### Authorization

Determines user permissions.

Example

```
ROLE_ADMIN

ROLE_USER
```

---

### BCrypt

One-way password hashing algorithm.

Cannot be decrypted.

---

### UserDetails

Spring Security representation of a logged-in user.

---

### UserDetailsService

Loads user information from the database.

---

### PasswordEncoder

Encrypts and verifies passwords.

---

### SecurityFilterChain

Defines security rules for incoming HTTP requests.

---

### DaoAuthenticationProvider

Authenticates users using `UserDetailsService` and `PasswordEncoder`.

---

### AuthenticationManager

Coordinates the authentication process.

---

# Current Authentication Flow

```text
Register User
     │
     ▼
UserService
     │
     ▼
PasswordEncoder (BCrypt)
     │
     ▼
H2 Database
──────────────────────────────────────
Login Request
     │
     ▼
Spring Security Filter
     │
     ▼
DaoAuthenticationProvider
     │
     ▼
CustomUserDetailsService
     │
     ▼
UserRepo.findByEmail()
     │
     ▼
H2 Database
     │
     ▼
UserDetails
     │
     ▼
PasswordEncoder.matches()
     │
     ▼
Authentication Success
     │
     ▼
Controller
     │
     ▼
Response
```
