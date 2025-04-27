package com.sivalabs.urlshortener.domain.models;

public record CreateShortUrlCmd(
        String originalUrl,
        boolean isPrivate,
        Integer expirationDays,
        Long userId) {}
