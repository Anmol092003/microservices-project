<<<<<<< HEAD
# Bus Reservation System : HCLTech Project (Training and DI)
=======
# 🔐 JWT Authentication Service (Spring Boot + Security + JPA)

A mini backend project demonstrating **Spring Boot + Spring Security + JWT** for secure authentication and authorization. Implements role-based access control with DTO-based request/response handling.

---

## 🚀 Features

* User authentication and authorization with **JWT tokens**
* REST APIs for **signup, login, and protected endpoints**
* Role-based access control (**ADMIN / USER** roles)
* Secure password storage with Spring Security
* DTO mapping for clean request/response handling

---

## 🛠 Tech Stack

* **Java 17**
* **Spring Boot**
* **Spring Security**
* **Spring Data JPA (Hibernate)**
* **PostgreSQL / MySQL**
* **Maven**
* **Lombok**

---

## 📌 Database Schema

* **User** (`id`, `username`, `password`, `roles`)

---

## 📌 API Endpoints

### Auth

* `POST /auth/signup` → Register new user
* `POST /auth/login` → Authenticate user and return JWT token

### Protected

* `GET /users/me` → Get current user details (requires valid token)
* `GET /admin/dashboard` → Admin-only endpoint

---

## ⚡ How to Run

1. Clone the repository:

   ```bash
   git clone https://github.com/yugaldekate/SpringBoot-JWT-Auth.git
   cd SpringBoot-JWT-Auth
   ```

2. Configure database in `application.yml`:

   ```yaml
   spring:
    datasource:
      username: postgres
      password: password
      url: jdbc:postgresql://localhost:5432/jwt_pgsql
      driver-class-name: org.postgresql.Driver
    jpa:
      hibernate:
        ddl-auto: create
      show-sql: true
      properties:
        hibernate:
          format_sql: true
      database: postgresql
      database-platform: org.hibernate.dialect.PostgreSQLDialect

   ```

3. Run the application:

   ```bash
   mvn spring-boot:run
   ```

4. Test with Postman:

   * Signup a new user
   * Login and get JWT token
   * Access protected endpoints with `Authorization: Bearer <token>`


>>>>>>> f4f9499 (Update README.md)
