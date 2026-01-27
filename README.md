# Spring Boot URL Shortener API

A REST API for URL shortening service built with Spring Boot.

**NOTE:** You can find Angular UI [here](https://github.com/sivaprasadreddy/angular-url-shortener)

## Tech Stack
* Java
* Spring Boot 
* Spring Security with JWT authentication
* Spring Data JPA
* PostgreSQL
* Flyway (for database migrations)
* SpringDoc OpenAPI (for API documentation)
* JUnit 5
* Testcontainers

## Getting Started

### Prerequisites
- Java 25
- Docker and Docker Compose

### Generating certs

```shell
# create rsa key pair
openssl genrsa -out keypair.pem 2048

# extract public key
openssl rsa -in keypair.pem -pubout -out public.pem

# create private key in PKCS#8 format
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem
```

### Running the Application
1. Clone the repository
2. Run the application:
   ```
   ./mvnw spring-boot:run
   ```
3. Access the Swagger UI at http://localhost:8080/swagger-ui/index.html
