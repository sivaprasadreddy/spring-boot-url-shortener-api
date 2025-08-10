# Authenticate User

- Use case: Authenticate a user with email and password and obtain a JWT to access protected endpoints.

## Pre-conditions
- User account exists with the given email and password.

## URL
- `POST /api/login`

## Required Headers
- Content-Type: application/json
- Accept: application/json

## Request JSON
```json
{
  "email": "user@example.com",
  "password": "secret"
}
```

Fields:
- email (string, required, email format)
- password (string, required)

## Success Response
- Status: 200 OK
- Body JSON:
```json
{
  "token": "<jwt-token>",
  "expiresAt": "2025-08-08T22:50:00Z",
  "email": "user@example.com",
  "name": "User Name",
  "role": "ROLE_USER"
}
```

## Error Responses
- 401 Unauthorized: Invalid credentials (no body or default error from framework)
- 400 Bad Request: Validation errors for request body (ProblemDetail)

### ProblemDetail format
```json
{
  "type": "about:blank",
  "title": "Bad Request",
  "status": 400,
  "detail": "<message>",
  "instance": "/api/login"
}
```

## cURL Example
```shell
curl -sS -X POST \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "secret"
  }' \
  http://localhost:8080/api/login
```
