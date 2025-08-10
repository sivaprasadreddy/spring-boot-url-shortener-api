# List My Short URLs

- Use case: Retrieve a paginated list of short URLs created by the currently authenticated user.

## Pre-conditions
- Valid JWT access token.

## URL
- `GET /api/my-urls`

## Query Parameters
- page (integer, optional, default: 1): Page number starting from 1.

## Required Headers
- Accept: application/json
- Authorization: Bearer <jwt-token>

## Success Response
- Status: 200 OK
- Body JSON (PagedResult<ShortUrlDto>):

```json
{
  "data": [
    {
      "id": 201,
      "shortKey": "me123",
      "originalUrl": "https://example.com/mine",
      "isPrivate": true,
      "createdBy": { "id": 7, "name": "Me" },
      "clickCount": 5,
      "createdAt": "2025-08-01T10:15:30",
      "expiresAt": null
    }
  ],
  "pageNumber": 1,
  "totalPages": 2,
  "totalElements": 20,
  "isFirst": true,
  "isLast": false,
  "hasNext": true,
  "hasPrevious": false
}
```

## Error Responses
- 401 Unauthorized: Missing/invalid token
- 403 Forbidden: If access is denied
- 400 Bad Request: Validation errors for query parameters (rare)

## cURL Example
```
curl -sS \
  -H "Accept: application/json" \
  -H "Authorization: Bearer <jwt-token>" \
  "http://localhost:8080/api/my-urls?page=1"
```