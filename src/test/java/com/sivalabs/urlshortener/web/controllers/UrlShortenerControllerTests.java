package com.sivalabs.urlshortener.web.controllers;

import com.sivalabs.urlshortener.BaseIT;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import static org.assertj.core.api.Assertions.assertThat;

class UrlShortenerControllerTests extends BaseIT {

    @Test
    void shouldGetPublicShortenedUrls() {
        MvcTestResult result = mockMvcTester.get()
                .uri("/api/short-urls")
                .exchange();

        assertThat(result).hasStatusOk();
    }

    @Test
    void shouldCreateShortUrl() {
        MvcTestResult result = mockMvcTester.post()
                .uri("/api/short-urls")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "originalUrl": "https://start.spring.io/"
                        }
                        """)
                .exchange();

        assertThat(result).hasStatus(HttpStatus.CREATED);
    }

    @Test
    void shouldRedirectToOriginalUrl() {
        MvcTestResult result = mockMvcTester.get()
                .uri("/s/docker")
                .exchange();

        assertThat(result)
                .hasStatus(HttpStatus.FOUND)
                .hasViewName("redirect:https://www.docker.com");
    }

    @Test
    void shouldReturnErrorWhenShortUrlNotFound() {
        MvcTestResult result = mockMvcTester.get()
                .uri("/s/notfound")
                .exchange();

        assertThat(result)
                .hasStatus(HttpStatus.FOUND)
                .hasViewName("redirect:http://localhost:4200/not-found");
    }

    @Test
    @WithMockUser
    void shouldShowMyUrlsPage() {
        MvcTestResult result = mockMvcTester.get()
                .uri("/api/my-urls")
                .exchange();

        assertThat(result).hasStatusOk();
    }

    @Test
    @WithMockUser
    void shouldDeleteUrls() {
        MvcTestResult result = mockMvcTester.delete()
                .uri("/api/short-urls")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "ids": [1, 2, 3]
                        }
                        """)
                .exchange();

        assertThat(result).hasStatus(HttpStatus.OK);
    }
}
