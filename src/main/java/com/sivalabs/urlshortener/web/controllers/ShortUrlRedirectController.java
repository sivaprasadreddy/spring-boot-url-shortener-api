package com.sivalabs.urlshortener.web.controllers;

import com.sivalabs.urlshortener.ApplicationProperties;
import com.sivalabs.urlshortener.domain.models.ShortUrlDto;
import com.sivalabs.urlshortener.domain.services.ShortUrlService;
import com.sivalabs.urlshortener.web.utils.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.URI;

@Controller
class ShortUrlRedirectController {
    private final ShortUrlService shortUrlService;
    private final SecurityUtils securityUtils;
    private final ApplicationProperties properties;

    ShortUrlRedirectController(ShortUrlService shortUrlService, SecurityUtils securityUtils, ApplicationProperties properties) {
        this.shortUrlService = shortUrlService;
        this.securityUtils = securityUtils;
        this.properties = properties;
    }

    @GetMapping("/s/{shortKey}")
    ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String shortKey) {
        var currentUserId = securityUtils.getCurrentUserId();
        var optionalShortUrl = shortUrlService.accessShortUrl(shortKey, currentUserId);
        var redirectUrl = optionalShortUrl.map(ShortUrlDto::originalUrl).orElse(properties.baseUrl() +"/not-found");
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(redirectUrl)).build();
    }
}
