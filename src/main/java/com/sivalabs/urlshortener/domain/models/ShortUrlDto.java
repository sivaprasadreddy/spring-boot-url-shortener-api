package com.sivalabs.urlshortener.domain.models;

import java.time.LocalDateTime;

public record ShortUrlDto(
        Long id,
        String shortKey,
        String originalUrl,
        boolean isPrivate,
        UrlCreatedBy createdBy,
        Long clickCount,
        LocalDateTime createdAt,
        LocalDateTime expiresAt
) {

    public record UrlCreatedBy(Long id, String name){}
}
