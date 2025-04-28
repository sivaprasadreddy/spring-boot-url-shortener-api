package com.sivalabs.urlshortener;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "app")
@Validated
public record ApplicationProperties (
    @NotBlank
    @DefaultValue("http://localhost:4200")
    String baseUrl,
    @Min(1)
    @Max(365)
    @DefaultValue("30")
    int defaultExpirationDays,
    @DefaultValue("true")
    Boolean validateOriginalUrl,
    @Min(5)
    @Max(100)
    @DefaultValue("10")
    int pageSize,
    @Valid
    JwtProperties jwt){

    public record JwtProperties(
        @NotBlank
        String issuer,
        @NotNull
        @Positive
        Long expiresInSeconds,
        @NotNull
        RSAPublicKey publicKey,
        @NotNull
        RSAPrivateKey privateKey){}
}
