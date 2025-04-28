package com.sivalabs.urlshortener.domain.exceptions;

import io.github.wimdeblauwe.errorhandlingspringbootstarter.ResponseErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@ResponseErrorCode("BAD_REQUEST")
public class InvalidURLException extends RuntimeException {
    public InvalidURLException(String message) {
        super(message);
    }

    public static InvalidURLException of(String url) {
        return new InvalidURLException("Invalid URL: " + url);
    }
}
