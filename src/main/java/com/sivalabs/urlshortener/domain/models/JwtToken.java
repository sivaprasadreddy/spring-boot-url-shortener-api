package com.sivalabs.urlshortener.domain.models;

import java.time.Instant;

public record JwtToken(String token, Instant expiresAt) {}
