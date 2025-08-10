# Redirect to Original URL

- Use case: Redirect the requester from a short key to the original URL.

## Pre-conditions
- The short key may or may not exist. If not found, redirect to `<baseUrl>/not-found`.

## URL
- `GET /s/{shortKey}`

## Path Parameters
- shortKey (string, required): The short key for the URL.

## Required Headers
- None required; standard browser behavior applies.

## Success Response
- Status: 302 Found
- Headers:
  - Location: <original-url>
- Body: empty

## Not Found Behavior
- Status: 302 Found
- Headers:
  - Location: <baseUrl>/not-found
- Body: empty

## Error Responses
- None specific. Standard 4xx/5xx may apply in edge cases.


## cURL Example
- To see redirect headers (without following the redirect):
```shell
curl -i "http://localhost:8080/s/abc123"
```
- To follow the redirect:
```shell
curl -L "http://localhost:8080/s/abc123"
```