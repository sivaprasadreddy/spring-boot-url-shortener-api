# Create Short URL

- Use case: Create a new short URL for an original URL. Optionally mark as private and/or set expiration in the past days.

## Pre-conditions
- None. Authentication is not required by the current security configuration; if authenticated, the createdBy will be the current user; otherwise, it may be null.
- Original URL must be valid and reachable as per service validation.

## URL
- `POST /api/short-urls`

## Required Headers
- Content-Type: application/json
- Accept: application/json
- Authorization: Bearer <token> (optional)

## Request JSON
```json
{
  "originalUrl": "example.com/some/page",
  "isPrivate": false,
  "expirationInDays": 30
}
```

Notes:
- originalUrl (string, required). If it doesn’t start with http:// or https://, the API will treat it as http://<value>.
- isPrivate (boolean, optional). Defaults to false when omitted or null.
- expirationInDays (integer, optional). Number of days from creation after which the link expires.

## Success Response
- Status: 201 Created
- Body JSON (ShortUrlDto):

```json
{
  "id": 123,
  "shortKey": "xyz789",
  "originalUrl": "http://example.com/some/page",
  "isPrivate": false,
  "createdBy": { "id": 1, "name": "Alice" },
  "clickCount": 0,
  "createdAt": "2025-08-08T21:55:00",
  "expiresAt": "2025-09-07T21:55:00"
}
```

## Error Responses
- 400 Bad Request (ProblemDetail): e.g., Invalid URL

```jsoon
{
  "type": "about:blank",
  "title": "Invalid URL",
  "status": 400,
  "detail": "<reason>",
  "instance": "/api/short-urls"
}
```

## cURL Example
```shell
curl -sS -X POST \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -H "Authorization: Bearer <jwt-token>" \
  -d '{
    "originalUrl": "example.com/some/page",
    "isPrivate": false,
    "expirationInDays": 30
  }' \
  http://localhost:8080/api/short-urls
```
