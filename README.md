# Employee Management System

A High-Tech Employee Management System with a Chatbot, built using Spring Boot, Hibernate, MySQL, and Thymeleaf.

## Features
- **CRUD Operations**: Add, Update, Delete, View employees.
- **Chatbot**: Built-in Virtual Assistant for HR queries.
- **High-Tech UI**: Dark mode, glassmorphism, and responsive design.
- **Dockerized**: Easy deployment.

## Prerequisites
- Docker & Docker Compose (Recommended)
- OR Java 17+ and Maven (if running locally without Docker)

## How to Run (Recommended)

1. Open a terminal in the project root.
2. Run the following command:
   ```bash
   docker-compose up --build
   ```
3. Access the application at: `http://localhost:8080`

## How to Run (Manual)

1. Ensure MySQL is running on port 3306 (Update `application.properties` if needed).
2. Run:
   ```bash
   mvn spring-boot:run
   ```
3. Access at `http://localhost:8080`

## Project Structure
- `src/main/java`: Backend Source Code
- `src/main/resources/templates`: HTML Views (Thymeleaf)
- `src/main/resources/static`: CSS & JS
