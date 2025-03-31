package com.sivalabs.urlshortener.web.controller;

import com.sivalabs.urlshortener.ApplicationProperties;
import com.sivalabs.urlshortener.domain.model.PagedResult;
import com.sivalabs.urlshortener.domain.model.ShortUrlDto;
import com.sivalabs.urlshortener.domain.service.ShortUrlService;
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
public class AdminController {
    private final ShortUrlService shortUrlService;
    private final ApplicationProperties properties;

    public AdminController(ShortUrlService shortUrlService, ApplicationProperties properties) {
        this.shortUrlService = shortUrlService;
        this.properties = properties;
    }

    @GetMapping("/short-urls")
    public PagedResult<ShortUrlDto> getAllShortUrls(@RequestParam(defaultValue = "1") int page) {
        return shortUrlService.findAllShortUrls(page, properties.getPageSize());
    }
}
