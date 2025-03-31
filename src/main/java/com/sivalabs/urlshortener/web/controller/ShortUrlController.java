package com.sivalabs.urlshortener.web.controller;

import com.sivalabs.urlshortener.ApplicationProperties;
import com.sivalabs.urlshortener.domain.model.CreateShortUrlCmd;
import com.sivalabs.urlshortener.domain.model.PagedResult;
import com.sivalabs.urlshortener.domain.model.ShortUrlDto;
import com.sivalabs.urlshortener.domain.service.ShortUrlService;
import com.sivalabs.urlshortener.web.dto.CreateShortUrlRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api")
@Tag(name = "Short URLs")
class ShortUrlController {
    private static final Logger log = LoggerFactory.getLogger(ShortUrlController.class);

    private final ShortUrlService shortUrlService;
    private final SecurityUtils securityUtils;
    private final ApplicationProperties properties;

    ShortUrlController(ShortUrlService shortUrlService, SecurityUtils securityUtils, ApplicationProperties properties) {
        this.shortUrlService = shortUrlService;
        this.securityUtils = securityUtils;
        this.properties = properties;
    }

    @GetMapping("/short-urls")
    PagedResult<ShortUrlDto> showShortUrls(@RequestParam(defaultValue = "1") int page) {
        return shortUrlService.getPublicShortUrls(page, properties.getPageSize());
    }

    @PostMapping("/short-urls")
    ResponseEntity<ShortUrlDto> createShortUrl(
            @RequestBody @Valid CreateShortUrlRequest request) {
        var originalUrl = request.getCleanedOriginalUrl();
        var isPrivate = request.isPrivate() != null && request.isPrivate();
        var cmd = new CreateShortUrlCmd(
                originalUrl, isPrivate,
                request.expirationInDays(),
                securityUtils.getCurrentUserId()
        );
        var shortUrlDto = shortUrlService.createShortUrl(cmd);
        return ResponseEntity.status(HttpStatus.CREATED).body(shortUrlDto);
    }

    @GetMapping("/my-urls")
    @SecurityRequirement(name = "Bearer")
    PagedResult<ShortUrlDto> myShortenedUrls(@RequestParam(defaultValue = "1") int page) {
        var currentUserId = securityUtils.getCurrentUserId();
        return shortUrlService.getMyShortUrls(currentUserId, page, properties.getPageSize());
    }

    @DeleteMapping("/short-urls")
    @SecurityRequirement(name = "Bearer")
    public void deleteUrls(@RequestBody DeleteRequest request) {
        Set<Long> ids = request.ids();
        if (ids == null || ids.isEmpty()) {
            log.info("No short_url ids selected for deletion");
            return;
        }
        var currentUserId = securityUtils.getCurrentUserId();
        shortUrlService.deleteUserShortUrls(ids, currentUserId);
    }

    record DeleteRequest(Set<Long> ids) {}
}
