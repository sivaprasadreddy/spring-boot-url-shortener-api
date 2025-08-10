# Register User

- Use case: Register a new user account.

## Pre-conditions
- Email should not yet exist in the system.

## URL
- `POST /api/users`

## Required Headers
- Content-Type: application/json
- Accept: application/json

## Request JSON
```json
{
  "email": "new.user@example.com",
  "password": "secret",
  "name": "New User"
}
```

Fields:
- email (string, required, email format)
- password (string, required)
- name (string, required)

## Success Response
- Status: 201 Created
- Body JSON:
```json
{
  "email": "new.user@example.com",
  "name": "New User",
  "role": "ROLE_USER"
}
```

## Error Responses
- 409 Conflict: Email already exists (ProblemDetail)
- 400 Bad Request: Validation errors (ProblemDetail)

### ProblemDetail format
```json
{
  "type": "about:blank",
  "title": "Email already exists",
  "status": 409,
  "detail": "Email already exists",
  "instance": "/api/users"
}
```

## cURL Example
```shell
curl -sS -X POST \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{
    "email": "new.user@example.com",
    "password": "secret",
    "name": "New User"
  }' \
  http://localhost:8080/api/users
```