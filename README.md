# Finance Dashboard - Backend API

This repository contains the backend implementation for the Finance Dashboard assignment. It is built using **Java 21**, **Spring Boot 3**, **Spring Security**, **Spring Data JPA**, and **PostgreSQL** in a clear Modular Monolith architecture.

## 1. Setup Instructions

### Prerequisites
- Java 17 or higher (tested on Java 21)
- Maven
- PostgreSQL running locally on port `5432`

### Database Configuration
Ensure you have a PostgreSQL server running. Create a standard database (default is `postgres`).
If your credentials are not `postgres:postgres`, update the `spring.datasource` properties inside `src/main/resources/application.properties`.

### Running the Application
From the root of `Backend` folder, run:
```bash
./mvnw clean spring-boot:run
```
The application will launch on **http://localhost:8080**.

---

## 2. API Documentation

To make evaluating the API as seamless as possible, this application integrates **Swagger UI**.
Start the application and navigate to:
**[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

Through Swagger, you can visually inspect models and test all endpoints directly.

---

## 3. Assumptions and Trade-offs

During implementation, specific decisions were made prioritizing clean code and maintainability over unnecessary overhead:

1. **Modular Monolith over Microservices**: As an MVP assessment, standing up microservices introduces unnecessary operational complexity. A cleanly structured monolith achieves strong logical separation of concerns without creating painful infrastructure overhead.
2. **Soft Deletion**: For Financial Records, a `deleted` boolean flag is toggled on deletion instead of issuing a hard `DELETE` query to the database. This allows for data recovery and better historic auditing.
3. **Role-based Authentication (RBAC)**: Custom JWT (JSON Web Tokens) stateless authentication was implemented. While stateful sessions are easier, JWT perfectly portrays scalable API interaction between separate frontends and backends.

---

## 4. Logical Workflow & Access Rules

The application uses standard Spring Method Security (`@PreAuthorize`) over HTTP Routes to enforce strictly decoupled business rules.
- **VIEWER**: Read-only access to Financial Records (`GET /api/records`). Blocked from analytics and editing.
- **ANALYST**: Read access to Financial Records (`GET /api/records`) and complete access to Analytics Summaries (`GET /api/dashboard/summary`).
- **ADMIN**: Administrative power to create, update, and soft-delete financial records.

---

## 5. Global Validation & Reliability

All REST endpoints utilize validation constraints (`@Valid`, `@NotNull`, `@NotBlank`, `@DecimalMin`).
Instead of sending back ugly raw Java Stacktraces, a `GlobalExceptionHandler` (`@ControllerAdvice`) intercepts internal exceptions (such as access denials, missing records, or malformed constraints) and automatically repackages them into neat, informative JSON structures.
