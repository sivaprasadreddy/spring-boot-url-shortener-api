package com.sivalabs.urlshortener.security;

import java.time.Instant;

public record JwtToken(String token, Instant expiresAt) {}
