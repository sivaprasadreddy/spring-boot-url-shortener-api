package com.sivalabs.urlshortener.domain.services;

import com.sivalabs.urlshortener.ApplicationProperties;
import com.sivalabs.urlshortener.domain.entities.ShortUrl;
import com.sivalabs.urlshortener.domain.entities.User;
import com.sivalabs.urlshortener.domain.exceptions.InvalidURLException;
import com.sivalabs.urlshortener.domain.mappers.EntityMapper;
import com.sivalabs.urlshortener.domain.models.CreateShortUrlCmd;
import com.sivalabs.urlshortener.domain.models.PagedResult;
import com.sivalabs.urlshortener.domain.models.ShortUrlDto;
import com.sivalabs.urlshortener.domain.repositories.ShortUrlRepository;
import com.sivalabs.urlshortener.domain.repositories.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class ShortUrlService {
    private final ShortUrlRepository shortUrlRepository;
    private final UserRepository userRepository;
    private final EntityMapper entityMapper;
    private final ApplicationProperties properties;

    public ShortUrlService(ShortUrlRepository shortUrlRepository, UserRepository userRepository, EntityMapper entityMapper, ApplicationProperties properties) {
        this.shortUrlRepository = shortUrlRepository;
        this.userRepository = userRepository;
        this.entityMapper = entityMapper;
        this.properties = properties;
    }

    @Transactional
    public ShortUrlDto createShortUrl(CreateShortUrlCmd cmd) {
        if(properties.validateOriginalUrl()) {
            boolean urlExists = UrlExistenceValidator.isUrlExists(cmd.originalUrl());
            if(!urlExists) {
                throw InvalidURLException.of(cmd.originalUrl());
            }
        }
        String shortKey = generateUniqueShortKey();
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setShortKey(shortKey);
        shortUrl.setOriginalUrl(cmd.originalUrl());

        if(cmd.userId() != null) {
            shortUrl.setPrivate(cmd.isPrivate());
            var expirationDays = cmd.expirationDays();
            LocalDateTime expiresAt = null;
            if (expirationDays != null && expirationDays > 0) {
                expiresAt = LocalDateTime.now().plusDays(expirationDays);
            }
            shortUrl.setExpiresAt(expiresAt);
            User user = userRepository.findById(cmd.userId()).orElseThrow();
            shortUrl.setCreatedBy(user);
        } else {
            shortUrl.setPrivate(false);
            shortUrl.setExpiresAt(LocalDateTime.now().plusDays(properties.defaultExpirationDays()));
        }
        shortUrlRepository.save(shortUrl);
        return entityMapper.toDto(shortUrl);
    }

    private String generateUniqueShortKey() {
        String shortKey;
        do {
            shortKey = RandomUtils.generateRandomShortKey();
        } while (shortUrlRepository.existsByShortKey(shortKey));
        return shortKey;
    }

    public PagedResult<ShortUrlDto> findAllShortUrls(int page, int size) {
        Pageable pageable = getPageable(page, size);
        var shortUrlsPage =  shortUrlRepository.findAllShortUrls(pageable).map(entityMapper::toDto);
        return PagedResult.from(shortUrlsPage);
    }

    public PagedResult<ShortUrlDto> getPublicShortUrls(int page, int size) {
        Pageable pageable = getPageable(page, size);
        var shortUrlsPage = shortUrlRepository.findPublicShortUrls(pageable)
                .map(entityMapper::toDto);
        return PagedResult.from(shortUrlsPage);
    }

    public PagedResult<ShortUrlDto> getMyShortUrls(Long currentUserId, int page, int size) {
        Pageable pageable = getPageable(page, size);
        var shortUrlsPage = shortUrlRepository.findByCreatedById(currentUserId, pageable)
                .map(entityMapper::toDto);
        return PagedResult.from(shortUrlsPage);
    }

    private Pageable getPageable(int page, int size) {
        page = page < 0 ? 0 : page - 1;
        return PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
    }

    @Transactional
    public Optional<ShortUrlDto> accessShortUrl(String shortKey, Long currentUserId) {
        Optional<ShortUrl> optionalShortUrl = shortUrlRepository.findByShortKey(shortKey);
        if (optionalShortUrl.isEmpty()) {
            return Optional.empty();
        }
        ShortUrl shortUrl = optionalShortUrl.get();
        if (shortUrl.isExpired()) {
            return Optional.empty();
        }

        if (shortUrl.isPrivate() && shortUrl.getCreatedBy() != null) {
            if (!Objects.equals(shortUrl.getCreatedBy().getId(), currentUserId)) {
                return Optional.empty();
            }
        }
        // Increment click count
        shortUrl.incrementClickCount();
        shortUrlRepository.save(shortUrl);

        return optionalShortUrl.map(entityMapper::toDto);
    }

    @Transactional
    public void deleteUserShortUrls(Set<Long> ids, Long userId) {
        if (ids != null && !ids.isEmpty() && userId != null) {
            shortUrlRepository.deleteByIdInAndCreatedById(ids, userId);
        }
    }
}
