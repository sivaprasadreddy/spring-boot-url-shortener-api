# Delete Short URLs

- Use case: Delete one or more short URLs. Admins can delete any; regular users can delete only their own.

## Pre-conditions
- Valid JWT access token.
- Request must include at least one id.

## URL
- `DELETE /api/short-urls`

## Required Headers
- Content-Type: application/json
- Accept: application/json
- Authorization: Bearer <jwt-token>

## Request JSON
```json
{
  "ids": [101, 102, 103]
}
```

Fields:
- ids (array of number, required, non-empty)

## Success Response
- Status: 200 OK
- Body: empty

## Error Responses
- 401 Unauthorized: Missing/invalid token
- 403 Forbidden: If user attempts to delete resources they do not own (for non-admin)
- 400 Bad Request: If request body is malformed (ProblemDetail)

### ProblemDetail format (example)
```json
{
  "type": "about:blank",
  "title": "Bad Request",
  "status": 400,
  "detail": "<message>",
  "instance": "/api/short-urls"
}
```

## cURL Example

```shell
curl -sS -X DELETE \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -H "Authorization: Bearer <jwt-token>" \
  -d '{
    "ids": [101, 102, 103]
  }' \
  http://localhost:8080/api/short-urls
```
