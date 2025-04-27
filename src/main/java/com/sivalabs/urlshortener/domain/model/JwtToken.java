package com.sivalabs.urlshortener.domain.model;

import java.time.Instant;

public record JwtToken(String token, Instant expiresAt) {}
