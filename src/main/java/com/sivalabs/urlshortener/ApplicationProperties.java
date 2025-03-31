package com.sivalabs.urlshortener;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "app")
@Validated
public class ApplicationProperties {
    @NotBlank
    private String baseUrl = "http://localhost:4200";
    @Min(1)
    @Max(365)
    private int defaultExpirationDays = 30;
    private Boolean validateOriginalUrl = true;
    @Min(5)
    @Max(100)
    private int pageSize = 10;

    @Valid
    private JwtProperties jwt = new JwtProperties();

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public int getDefaultExpirationDays() {
        return defaultExpirationDays;
    }

    public void setDefaultExpirationDays(int defaultExpirationDays) {
        this.defaultExpirationDays = defaultExpirationDays;
    }

    public Boolean getValidateOriginalUrl() {
        return validateOriginalUrl;
    }

    public void setValidateOriginalUrl(Boolean validateOriginalUrl) {
        this.validateOriginalUrl = validateOriginalUrl;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public JwtProperties getJwt() {
        return jwt;
    }

    public void setJwt(JwtProperties jwt) {
        this.jwt = jwt;
    }

    public static class JwtProperties{
        @NotBlank
        private String issuer;
        @NotNull
        @Positive
        private Long expiresInSeconds;
        @NotNull
        private RSAPublicKey publicKey;
        @NotNull
        private RSAPrivateKey privateKey;

        public String getIssuer() {
            return issuer;
        }

        public void setIssuer(String issuer) {
            this.issuer = issuer;
        }

        public Long getExpiresInSeconds() {
            return expiresInSeconds;
        }

        public void setExpiresInSeconds(Long expiresInSeconds) {
            this.expiresInSeconds = expiresInSeconds;
        }

        public RSAPublicKey getPublicKey() {
            return publicKey;
        }

        public void setPublicKey(RSAPublicKey publicKey) {
            this.publicKey = publicKey;
        }

        public RSAPrivateKey getPrivateKey() {
            return privateKey;
        }

        public void setPrivateKey(RSAPrivateKey privateKey) {
            this.privateKey = privateKey;
        }
    }
}
