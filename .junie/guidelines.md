# Developer Guidelines

This document provides guidelines for developing a Spring Boot project. 
It includes coding standards, Spring Boot best practices and testing recommendations to follow.

### Prerequisites
- Java 21 or later
- Docker and Docker Compose
- Maven (or use the included Maven wrapper)

### Project Structure

Follow package-by-feature/module and in each module package-by-layer code organization style:

```shell
project-root/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/mycompany/projectname/
│   │   │       ├── config/
│   │   │       ├── module1/
│   │   │       │     ├── api/
│   │   │       │     │   ├── controllers/
│   │   │       │     │   └── dtos/
│   │   │       │     ├── config/
│   │   │       │     ├── domain/
│   │   │       │     │   ├── entities/
│   │   │       │     │   ├── exceptions/
│   │   │       │     │   ├── mappers/
│   │   │       │     │   ├── models/
│   │   │       │     │   ├── repositories/
│   │   │       │     │   └── services/
│   │   │       │     ├── jobs/
│   │   │       │     ├── eventhandlers/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│   │   └── java/
│   │   │   └── com/mycompany/projectname/
│   │   │       ├── module1/
│   │   │       │     ├── api/
│   │   │       │     │   ├── controllers/
│   │   │       │     ├── domain/
│   │   │       │     │   └── services/
└── README.md
```

1. **Web Layer** (`com.companyname.projectname.module.api`):
   - Controllers handle HTTP requests and responses
   - DTOs for request/response data
   - Global exception handling

2. **Service Layer** (`com.companyname.projectname.module.domain.services`):
   - Business logic implementation
   - Transaction management

3. **Repository Layer** (`com.companyname.projectname.module.domain.repositories`):
   - Spring Data JPA repositories
   - Database access

4. **Entity Layer** (`com.companyname.projectname.module.domain.entities`):
   - JPA entities representing database tables

5. **Model Layer** (`com.companyname.projectname.module.domain.models`):
   - DTOs for domain objects
   - Command objects for operations

6. **Mapper Layer** (`com.companyname.projectname.module.domain.mappers`):
   - Converters from DTOs to JPA entities and vice-versa

7. **Exceptions** (`com.companyname.projectname.module.domain.exceptions`):
   - Custom exceptions 

8. **Config** (`com.companyname.projectname.module.config`):
   - Spring Boot configuration classes such as WebMvcConfig, WebSecurityConfig, etc.

### Java Code Style Guidelines

1. **Java Code Style**:
   - Use Java 21 features where appropriate (records, text blocks, pattern matching, etc.)
   - Follow standard Java naming conventions
   - Use meaningful variable and method names
   - Use `public` access modifier only when necessary

2. **Testing Style**:
   - Use descriptive test method names
   - Follow the Given-When-Then pattern
   - Use AssertJ for assertions

### Spring Boot Code Style Guidelines
1. Dependency Injection Style
   * Don't use Spring Field Injection in production code.
   * Use Constructor Injection without adding `@Autowired`.

2. Transactional Boundaries
   * Annotate `@Service` classes with `@Transactional(readOnly=true)` to manage transactions declaratively.
   * Annotate methods in `@Service` classes that perform write operations with `@Transactional`.
   * Keep transactions as short as possible.

3. Don't use JPA entities in the "web" layer
   * Instead, create dedicated Request/Response objects as Java records.
   * Use Jakarta Validation annotations on Request object.

4. Create custom Spring Data JPA methods with meaningful method names using JPQL queries instead of using long derived query method names.

5. Create usecase specific Command objects and pass them to the "service" layer methods to perform create or update operations.

6. Application Configuration:
   * Create all the application-specific configuration properties with a common prefix in `application.properties` file.
   * Use Typed Configuration with `@ConfigurationProperties` with validations.

7. Implement Global Exception Handling:
   * `@ControllerAdvice`/`@RestControllerAdvice` with `@ExceptionHandler` methods.
   * Return consistent error payloads (e.g. a standard `ErrorResponse` DTO).

8. Logging:
   * Never use `System.out.println()` for production logging. 
   * Use SLF4J logging.

9. Use WebJars for service static content.

10. Don't use Lombok.


### Database Schema Management
Use Flyway for database migrations:

- Migration scripts should be in `src/main/resources/db/migration`
- Naming convention: `V{version}__{description}.sql`
- Hibernate is configured with `ddl-auto=validate` to ensure schema matches entities

### Test Best Practices
1. **Unit Tests**: Test individual components in isolation using mocks if required
2. **Integration Tests**: Test interactions between components using Testcontainers
3. **Use descriptive test names** that explain what the test is verifying
4. **Follow the Given-When-Then pattern** for a clear test structure
5. **Use AssertJ for assertions** for more readable assertions
6. **Mock external dependencies** in unit tests
7. **Use Testcontainers for integration tests** to test with real databases, message brokers, etc
8. **TestcontainersConfiguration.java**: Configures database, message broker, etc containers for tests
9. **BaseIT.java**: Base class for integration tests that sets up:
   - Spring Boot test context using a random port
   - MockMvcTester for HTTP requests
   - Import `TestcontainersConfiguration.java`

