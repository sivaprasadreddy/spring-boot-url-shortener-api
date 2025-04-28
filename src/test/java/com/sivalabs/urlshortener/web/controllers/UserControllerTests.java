package com.sivalabs.urlshortener.web.controllers;

import com.sivalabs.urlshortener.BaseIT;
import com.sivalabs.urlshortener.web.dtos.LoginResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import static org.assertj.core.api.Assertions.assertThat;

@Sql("/test-data.sql")
class UserControllerTests extends BaseIT {

    @Test
    void shouldLoginSuccessfully() {
        MvcTestResult result =
                mockMvcTester.post()
                        .uri("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "email": "admin@gmail.com",
                            "password": "admin"
                        }
                        """)
                        .exchange();

        assertThat(result)
                .hasStatusOk()
                .bodyJson()
                .convertTo(LoginResponse.class)
                .satisfies(loginResponse -> {
                    assertThat(loginResponse.token()).isNotEmpty();
                    assertThat(loginResponse.expiresAt()).isNotNull();
                    assertThat(loginResponse.email()).isEqualTo("admin@gmail.com");
                });
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        MvcTestResult result =
            mockMvcTester.post()
                .uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email": "testuser@gmail.com",
                            "password": "secret123",
                            "name": "Test User"
                        }
                        """)
                .exchange();
        assertThat(result).hasStatus(HttpStatus.CREATED);
    }
}