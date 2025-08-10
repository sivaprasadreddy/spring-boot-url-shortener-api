# Admin: List All Short URLs

- Use case: Admin retrieves a paginated list of all short URLs in the system.

## Pre-conditions
- Valid JWT access token with ROLE_ADMIN role.

## URL
- `GET /api/admin/short-urls`

## Query Parameters
- page (integer, optional, default: 1): Page number starting from 1.

## Required Headers
- Accept: application/json
- Authorization: Bearer <admin-jwt-token>

## Success Response
- Status: 200 OK
- Body JSON (PagedResult<ShortUrlDto>):

```json
{
  "data": [
    {
      "id": 301,
      "shortKey": "adm123",
      "originalUrl": "https://example.com",
      "isPrivate": false,
      "createdBy": { "id": 2, "name": "Bob" },
      "clickCount": 99,
      "createdAt": "2025-08-01T10:15:30",
      "expiresAt": null
    }
  ],
  "pageNumber": 1,
  "totalPages": 5,
  "totalElements": 50,
  "isFirst": true,
  "isLast": false,
  "hasNext": true,
  "hasPrevious": false
}
```

## Error Responses
- 401 Unauthorized: Missing/invalid token
- 403 Forbidden: If token does not have ADMIN role (ProblemDetail with title "Access Denied")

## cURL Example
```shell
curl -sS \
  -H "Accept: application/json" \
  -H "Authorization: Bearer <admin-jwt-token>" \
  "http://localhost:8080/api/admin/short-urls?page=1"
```
