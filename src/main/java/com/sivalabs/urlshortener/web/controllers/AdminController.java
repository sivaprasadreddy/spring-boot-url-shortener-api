package com.sivalabs.urlshortener.web.controllers;

import com.sivalabs.urlshortener.ApplicationProperties;
import com.sivalabs.urlshortener.domain.models.PagedResult;
import com.sivalabs.urlshortener.domain.models.ShortUrlDto;
import com.sivalabs.urlshortener.domain.services.ShortUrlService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@SecurityRequirement(name = "Bearer")
@Tag(name = "Admin")
class AdminController {
    private final ShortUrlService shortUrlService;
    private final ApplicationProperties properties;

    AdminController(ShortUrlService shortUrlService, ApplicationProperties properties) {
        this.shortUrlService = shortUrlService;
        this.properties = properties;
    }

    @GetMapping("/short-urls")
    PagedResult<ShortUrlDto> getAllShortUrls(@RequestParam(defaultValue = "1") int page) {
        return shortUrlService.findAllShortUrls(page, properties.getPageSize());
    }
}
