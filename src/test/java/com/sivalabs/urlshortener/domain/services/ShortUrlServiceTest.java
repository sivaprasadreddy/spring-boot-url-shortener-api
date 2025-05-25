package com.sivalabs.urlshortener.domain.services;

import com.sivalabs.urlshortener.BaseIT;
import com.sivalabs.urlshortener.domain.models.ShortUrlDto;
import com.sivalabs.urlshortener.domain.repositories.ShortUrlRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Sql("/test-data.sql")
class ShortUrlServiceTest extends BaseIT {

    @Autowired
    private ShortUrlService shortUrlService;

    @Autowired
    private ShortUrlRepository shortUrlRepository;

    @Test
    void shouldNotAccessExpiredShortUrl() {
        // Find the ShortUrl with shortKey 'medium'
        var shortUrl = shortUrlRepository.findByShortKey("medium").orElseThrow();

        // Set the expiration date to the past
        shortUrl.setExpiresAt(LocalDateTime.now().minusDays(1));
        shortUrlRepository.save(shortUrl);

        // Try to access it
        Optional<ShortUrlDto> result = shortUrlService.accessShortUrl("medium", null);

        // Verify it's not accessible
        assertThat(result).isEmpty();
    }

    @Test
    void shouldAccessNonExpiredShortUrl() {
        // Find the ShortUrl with shortKey 'docker'
        var shortUrl = shortUrlRepository.findByShortKey("docker").orElseThrow();

        // Set the expiration date to the future
        shortUrl.setExpiresAt(LocalDateTime.now().plusDays(30));
        shortUrlRepository.save(shortUrl);

        // Try to access it
        Optional<ShortUrlDto> result = shortUrlService.accessShortUrl("docker", null);

        // Verify it's accessible
        assertThat(result).isPresent();
        assertThat(result.get().originalUrl()).isEqualTo("https://www.docker.com");
    }
}