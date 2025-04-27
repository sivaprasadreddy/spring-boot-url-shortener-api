# Spring Boot URL Shortener API

A RESTful API for URL shortening service built with Spring Boot.

## Features

- Create shortened URLs (public or private)
- Set expiration date for URLs
- User registration and authentication
- Role-based access control
- API documentation with Swagger/OpenAPI
- Containerized development environment

## Tech Stack

### Backend
* Java 21
* Spring Boot 3.4.4
* Spring Security with JWT authentication
* Spring Data JPA
* Spring Validation
* Spring Actuator (for monitoring and metrics)
* PostgreSQL 17
* Flyway (for database migrations)
* SpringDoc OpenAPI (for API documentation)

### DevOps & Tools
* Docker & Docker Compose
* Maven
* Spring Boot DevTools
* Spring Boot Docker Compose

### Testing
* JUnit 5
* Spring Boot Test
* Spring Security Test
* Testcontainers
* Rest Assured

## Architecture

The application follows a layered architecture:

1. **Web Layer**: Controllers handle HTTP requests and responses
   - REST controllers for API endpoints
   - Global exception handling
   - Request/response DTOs

2. **Service Layer**: Contains business logic
   - URL shortening logic
   - User management
   - Authentication and authorization

3. **Repository Layer**: Data access
   - Spring Data JPA repositories
   - Entity models

4. **Security**: JWT-based authentication
   - Stateless authentication
   - Role-based access control
   - Public and protected endpoints

## API Endpoints

- `POST /api/users` - Register a new user
- `POST /api/login` - Authenticate user and get JWT token
- `GET /api/short-urls` - List public short URLs (paginated)
- `POST /api/short-urls` - Create a new short URL
- `GET /api/my-urls` - List user's own short URLs (authenticated)
- `DELETE /api/short-urls` - Delete user's short URLs (authenticated)
- `GET /api/admin/**` - Admin endpoints (requires ADMIN role)

## Database Schema

The application uses two main entities:
- **User**: Stores user information for authentication
- **ShortUrl**: Stores shortened URL data with relationships to users

## Getting Started

### Prerequisites
- Java 21
- Docker and Docker Compose

### Running the Application
1. Clone the repository
2. Start the PostgreSQL database:
   ```
   docker compose up -d
   ```
3. Run the application:
   ```
   ./mvnw spring-boot:run
   ```
4. Access the API at http://localhost:9090
5. Access the Swagger UI at http://localhost:9090/swagger-ui.html
