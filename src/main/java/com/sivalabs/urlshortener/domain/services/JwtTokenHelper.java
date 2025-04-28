package com.sivalabs.urlshortener.domain.services;

import com.sivalabs.urlshortener.ApplicationProperties;
import com.sivalabs.urlshortener.domain.entities.User;
import com.sivalabs.urlshortener.domain.models.JwtToken;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class JwtTokenHelper {
    private final JwtEncoder encoder;
    private final ApplicationProperties properties;

    JwtTokenHelper(JwtEncoder encoder, ApplicationProperties properties) {
        this.encoder = encoder;
        this.properties = properties;
    }

    public JwtToken generateToken(User user) {
        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(properties.jwt().expiresInSeconds());
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(properties.jwt().issuer())
                .issuedAt(now)
                .expiresAt(expiresAt)
                .subject(user.getEmail())
                .claim("user_id", user.getId())
                //.claim("role", user.getRole().name())
                .claim("scope", user.getRole().name())
                .build();
        var token = this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return new JwtToken(token, expiresAt);
    }
}
