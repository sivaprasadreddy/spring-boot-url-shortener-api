package com.sivalabs.urlshortener.domain.model;

public record CreateShortUrlCmd(
        String originalUrl,
        boolean isPrivate,
        Integer expirationDays,
        Long userId) {}
