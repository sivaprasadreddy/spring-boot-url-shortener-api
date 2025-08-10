# List Public Short URLs

- Use case: Retrieve a paginated list of public short URLs.

## Pre-conditions
- None.

## URL
- `GET /api/short-urls`

## Query Parameters
- page (integer, optional, default: 1): Page number starting from 1.

## Required Headers
- Accept: application/json

## Success Response
- Status: 200 OK
- Body JSON (PagedResult<ShortUrlDto>):
```json
{
  "data": [
    {
      "id": 101,
      "shortKey": "abc123",
      "originalUrl": "https://example.com/long/path",
      "isPrivate": false,
      "createdBy": { "id": 1, "name": "Alice" },
      "clickCount": 42,
      "createdAt": "2025-08-01T10:15:30",
      "expiresAt": "2025-12-31T00:00:00"
    }
  ],
  "pageNumber": 1,
  "totalPages": 10,
  "totalElements": 100,
  "isFirst": true,
  "isLast": false,
  "hasNext": true,
  "hasPrevious": false
}
```

## Error Responses
- None specific. Standard 400/500 may apply.


## cURL Example
```shell
curl -sS \
  -H "Accept: application/json" \
  "http://localhost:8080/api/short-urls?page=1"
```
