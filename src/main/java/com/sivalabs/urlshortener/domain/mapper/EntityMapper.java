package com.sivalabs.urlshortener.domain.mapper;

import com.sivalabs.urlshortener.domain.entity.ShortUrl;
import com.sivalabs.urlshortener.domain.model.ShortUrlDto;
import com.sivalabs.urlshortener.domain.model.ShortUrlDto.UrlCreatedBy;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper {

    public ShortUrlDto toDto(ShortUrl shortUrl) {
        if (shortUrl == null) {
            return null;
        }
        UrlCreatedBy createdBy = null;
        if (shortUrl.getCreatedBy() != null) {
            createdBy = new UrlCreatedBy(
                shortUrl.getCreatedBy().getId(),
                shortUrl.getCreatedBy().getName()
            );
        }
        return new ShortUrlDto(
                shortUrl.getId(),
                shortUrl.getShortKey(),
                shortUrl.getOriginalUrl(),
                shortUrl.isPrivate(),
                createdBy,
                shortUrl.getClickCount(),
                shortUrl.getCreatedAt(),
                shortUrl.getExpiresAt()
        );
    }
}
