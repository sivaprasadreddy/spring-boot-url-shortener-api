package com.sivalabs.urlshortener.web.controller;

import com.sivalabs.urlshortener.ApplicationProperties;
import com.sivalabs.urlshortener.domain.service.ShortUrlService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    public String redirectToOriginalUrl(@PathVariable String shortKey) {
        var currentUserId = securityUtils.getCurrentUserId();
        var optionalShortUrl = shortUrlService.accessShortUrl(shortKey, currentUserId);
        return optionalShortUrl
                .map(shortUrlDto -> "redirect:" + shortUrlDto.originalUrl())
                .orElse("redirect:"+ properties.getBaseUrl() +"/not-found");
    }
}
