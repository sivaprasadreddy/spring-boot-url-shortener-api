package com.sivalabs.urlshortener.web.dto;

import java.time.Instant;

public record LoginResponse(
        String token,
        Instant expiresAt,
        String email,
        String name,
        String role) {}
