# Spring Boot Logger

Logging is one of the most important topics in Spring Boot because every real project uses logs to:

* Debug issues
* Track application flow
* Find production problems
* Monitor application health
* Audit important actions

---

# 1. What is Logger?

A **Logger** is used to print messages about what your application is doing.

Instead of writing:

```java
System.out.println("User Created");
```

We use

```java
logger.info("User Created");
```

Why?

Because logger is:

* Professional
* Faster
* Configurable
* Can save logs into files
* Different log levels
* Used in Production

---

# 2. Why Not System.out.println()?

Example

```java
System.out.println("Application Started");
```

Problems

❌ Cannot disable

❌ Cannot categorize

❌ Cannot save to file easily

❌ No timestamp

❌ No thread information

❌ Difficult to debug production issues

Logger solves all these problems.

---

# 3. What is SLF4J?

Spring Boot uses

**SLF4J**

Meaning

> Simple Logging Facade for Java

Think of it like

```
Application
      ↓
 SLF4J (Interface)
      ↓
 Logback (Implementation)
```

Spring Boot uses

```
SLF4J + Logback
```

by default.

---

# 4. Logging Architecture

```
Controller

↓

Service

↓

Repository

↓

Database
```

Each layer writes logs.

Example

```
Controller
↓

Request Received

↓

Service

↓

Business Logic Started

↓

Repository

↓

SQL Executed

↓

Database
```

---

# 5. Logger Flow

User Request

↓

Controller Log

↓

Service Log

↓

Repository Log

↓

Response

---

Example

```
User sends POST Request

↓

Controller
INFO Request Received

↓

Service
INFO Creating User

↓

Repository
INFO Saving User

↓

Database

↓

Controller
INFO Response Sent
```

---

# 6. Logger Dependency

No dependency required.

Spring Boot Starter Web already contains

```
spring-boot-starter-logging
```

which includes

* SLF4J
* Logback

---

# 7. Creating Logger

Old Way

```java
private static final Logger logger =
LoggerFactory.getLogger(UserService.class);
```

Imports

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
```

---

# 8. Logger Variable

```java
private static final Logger logger =
LoggerFactory.getLogger(UserService.class);
```

Meaning

```
private

Only this class can use it.

------------------

static

Only one logger object created.

------------------

final

Cannot change.

------------------

Logger

Logger type.

------------------

LoggerFactory

Creates logger object.
```

---

# 9. First Logger Example

```java
@Service
public class UserService {

    private static final Logger logger =
            LoggerFactory.getLogger(UserService.class);

    public void createUser(){

        logger.info("User Creation Started");

        logger.info("User Created Successfully");
    }

}
```

Console

```
INFO User Creation Started

INFO User Created Successfully
```

---

# 10. Logger Levels

There are 5 main log levels.

```
TRACE

↓

DEBUG

↓

INFO

↓

WARN

↓

ERROR
```

---

## TRACE

Lowest level

Very detailed logs.

Example

```
Method Entered

Variable Values

Loop Details

Execution Steps
```

Example

```java
logger.trace("Entered createUser()");
```

---

## DEBUG

Developer debugging information.

Example

```java
logger.debug("User Object {}", user);
```

Use for

* Variable values
* Method execution
* SQL values

---

## INFO

Most commonly used.

Shows normal application flow.

Example

```java
logger.info("User Created");
```

Examples

```
Application Started

Login Success

Order Created

Payment Completed
```

---

## WARN

Something unexpected happened.

Application still works.

Example

```java
logger.warn("Password expires in 2 days");
```

Examples

```
Invalid Input

Low Memory

Retry Happening
```

---

## ERROR

Application failed.

Example

```java
logger.error("Database Connection Failed");
```

Examples

```
Null Pointer

SQLException

File Missing

Server Error
```

---

# 11. Log Level Table

| Level | Purpose       | Example         |
| ----- | ------------- | --------------- |
| TRACE | Very detailed | Method Enter    |
| DEBUG | Debugging     | Variable Values |
| INFO  | Normal flow   | User Created    |
| WARN  | Warning       | Invalid Input   |
| ERROR | Failure       | Database Error  |

---

# 12. Which Level Should I Use?

Application Started

```
INFO
```

User Login

```
INFO
```

Variable

```
DEBUG
```

Method Enter

```
TRACE
```

Validation Warning

```
WARN
```

Exception

```
ERROR
```

---

# 13. Logger in Controller

```java
@RestController
public class UserController {

    private static final Logger logger =
            LoggerFactory.getLogger(UserController.class);

    @PostMapping("/users")
    public String save(){

        logger.info("POST /users API called");

        return "Success";
    }
}
```

Console

```
INFO POST /users API called
```

---

# 14. Logger in Service

```java
@Service
public class UserService {

    private static final Logger logger =
            LoggerFactory.getLogger(UserService.class);

    public void saveUser(){

        logger.info("Saving User");

    }
}
```

---

# 15. Logger in Repository

```java
@Repository
public class UserRepository {

    private static final Logger logger =
            LoggerFactory.getLogger(UserRepository.class);

}
```

Usually repository logs are not required because Hibernate logs SQL automatically.

---

# 16. Logging Objects

Wrong

```java
logger.info(user.toString());
```

Correct

```java
logger.info("User : {}", user);
```

Benefits

* Cleaner
* Faster
* Lazy evaluation
* No string concatenation

---

# 17. Logging Variables

Wrong

```java
logger.info("Id = " + id);
```

Correct

```java
logger.info("Id : {}", id);
```

Multiple variables

```java
logger.info("Id : {} Name : {}", id, name);
```

Output

```
Id : 1 Name : Nadeem
```

---

# 18. Logging Exceptions

Wrong

```java
catch(Exception e){

}
```

Correct

```java
catch(Exception e){

logger.error("Error while saving user", e);

}
```

Output

```
ERROR Error while saving user

java.lang.NullPointerException
```

---

# 19. Logger Output

```
2026-07-09 18:15:20

INFO

10234

main

com.example.demo.UserService

User Created Successfully
```

Contains

* Date
* Time
* Log Level
* Thread
* Package
* Message

---

# 20. application.properties

Show only INFO

```properties
logging.level.root=INFO
```

Show DEBUG

```properties
logging.level.root=DEBUG
```

Only your package

```properties
logging.level.com.example.demo=DEBUG
```

---

# 21. Save Logs into File

```properties
logging.file.name=logs/application.log
```

Run application

Automatically

```
Project

↓

logs

↓

application.log
```

---

# 22. Log Pattern

```properties
logging.pattern.console=%d %-5level %logger - %msg%n
```

Output

```
2026-07-09 INFO User Created
```

---

# 23. Example Project Flow

```
POST /users

↓

Controller

↓

logger.info("Request Received")

↓

Service

↓

logger.info("Validation Started")

↓

Repository

↓

Save User

↓

logger.info("Saved Successfully")

↓

Response
```

---

# 24. Best Practices

✅ Use `INFO` for normal flow.

✅ Use `DEBUG` while developing.

✅ Use `WARN` for recoverable issues.

✅ Use `ERROR` for exceptions.

✅ Use `{}` placeholders instead of `+`.

✅ Never log passwords, OTPs, JWT tokens, or sensitive information.

---

# 25. Interview Questions

### Q1. What is Logger?

A Logger records application events, execution flow, warnings, and errors for debugging and monitoring.

---

### Q2. Why use Logger instead of `System.out.println()`?

* Supports log levels
* Better performance
* Can write to files
* Includes timestamps and thread information
* Can be configured for different environments

---

### Q3. Which logging framework does Spring Boot use?

Spring Boot uses **SLF4J** as the logging API and **Logback** as the default logging implementation.

---

### Q4. What are the log levels?

1. TRACE
2. DEBUG
3. INFO
4. WARN
5. ERROR

---

### Q5. How do you create a logger?

```java
private static final Logger logger =
        LoggerFactory.getLogger(UserService.class);
```

---

### Q6. How do you log a variable?

```java
logger.info("User Id: {}", id);
```

---

### Q7. How do you log an exception?

```java
logger.error("Error while saving user", exception);
```

---

### Q8. How do you save logs to a file?

```properties
logging.file.name=logs/application.log
```

---

# 26. Real-Time Example

```java
@Service
public class UserService {

    private static final Logger logger =
            LoggerFactory.getLogger(UserService.class);

    public User createUser(User user) {

        logger.info("Create User request received");

        try {

            logger.debug("User details: {}", user);

            User saved = repository.save(user);

            logger.info("User created successfully with ID: {}", saved.getId());

            return saved;

        } catch (Exception e) {

            logger.error("Failed to create user", e);

            throw e;
        }
    }
}
