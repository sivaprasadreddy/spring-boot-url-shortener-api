package com.sivalabs.urlshortener.web.controllers;

import com.sivalabs.urlshortener.BaseIT;
import com.sivalabs.urlshortener.domain.entities.User;
import com.sivalabs.urlshortener.domain.models.JwtToken;
import com.sivalabs.urlshortener.domain.models.PagedResult;
import com.sivalabs.urlshortener.domain.models.ShortUrlDto;
import com.sivalabs.urlshortener.domain.repositories.UserRepository;
import com.sivalabs.urlshortener.domain.services.JwtTokenHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import static org.assertj.core.api.Assertions.assertThat;

@Sql("/test-data.sql")
class UrlShortenerControllerTests extends BaseIT {

    @Autowired
    JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldGetPublicShortenedUrls() {
        MvcTestResult result = mockMvcTester.get()
                .uri("/api/short-urls")
                .exchange();

        assertThat(result)
                .hasStatusOk()
                .bodyJson()
                .convertTo(PagedResult.class)
                .satisfies(pagedResult -> {
                    assertThat(pagedResult.data()).hasSize(10);
                    assertThat(pagedResult.totalElements()).isEqualTo(16);
                    assertThat(pagedResult.pageNumber()).isEqualTo(1);
                    assertThat(pagedResult.totalPages()).isEqualTo(2);
                    assertThat(pagedResult.hasNext()).isTrue();
                    assertThat(pagedResult.hasPrevious()).isFalse();
                    assertThat(pagedResult.isFirst()).isTrue();
                    assertThat(pagedResult.isLast()).isFalse();
                });
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

        assertThat(result)
                .hasStatus(HttpStatus.CREATED)
                .bodyJson()
                .convertTo(ShortUrlDto.class)
                .satisfies(shortUrlDto -> {
                    assertThat(shortUrlDto.id()).isNotNull();
                    assertThat(shortUrlDto.shortKey()).isNotEmpty();
                    assertThat(shortUrlDto.originalUrl()).isEqualTo("https://start.spring.io/");
                    assertThat(shortUrlDto.isPrivate()).isFalse();
                    assertThat(shortUrlDto.clickCount()).isEqualTo(0L);
                    assertThat(shortUrlDto.createdAt()).isNotNull();
                    assertThat(shortUrlDto.expiresAt()).isNotNull();
                });
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
    void shouldShowMyUrlsPage() {
        MvcTestResult result = mockMvcTester.get()
                .uri("/api/my-urls")
                .header(HttpHeaders.AUTHORIZATION, getJwtTokenHeaderValue("admin@gmail.com"))
                .exchange();

        assertThat(result)
                .hasStatusOk()
                .bodyJson()
                .convertTo(PagedResult.class)
                .satisfies(pagedResult -> {
                    assertThat(pagedResult.data()).hasSize(10);
                    assertThat(pagedResult.totalElements()).isEqualTo(18);
                    assertThat(pagedResult.pageNumber()).isEqualTo(1);
                    assertThat(pagedResult.totalPages()).isEqualTo(2);
                    assertThat(pagedResult.hasNext()).isTrue();
                    assertThat(pagedResult.hasPrevious()).isFalse();
                    assertThat(pagedResult.isFirst()).isTrue();
                    assertThat(pagedResult.isLast()).isFalse();

                });
    }

    @Test
    void shouldDeleteUrls() {
        MvcTestResult result = mockMvcTester.delete()
                .uri("/api/short-urls")
                .header(HttpHeaders.AUTHORIZATION, getJwtTokenHeaderValue("admin@gmail.com"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "ids": [1, 2, 3]
                        }
                        """)
                .exchange();

        assertThat(result).hasStatus(HttpStatus.OK);
    }

    private String getJwtTokenHeaderValue(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        JwtToken jwtToken = jwtTokenHelper.generateToken(user);
        return "Bearer "+jwtToken.token();
    }
}
