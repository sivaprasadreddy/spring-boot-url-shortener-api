package com.sivalabs.urlshortener.web;

import com.sivalabs.urlshortener.domain.exceptions.BadRequestException;
import com.sivalabs.urlshortener.domain.exceptions.EmailAlreadyExistsException;
import com.sivalabs.urlshortener.domain.exceptions.InvalidURLException;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ProblemDetail handleEmailAlreadyExists(EmailAlreadyExistsException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(CONFLICT, e.getMessage());
        problemDetail.setTitle("Email already exists");
        return problemDetail;
    }
    
    @ExceptionHandler(InvalidURLException.class)
    public ProblemDetail handleInvalidUrl(InvalidURLException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(BAD_REQUEST, e.getMessage());
        problemDetail.setTitle("Invalid URL");
        return problemDetail;
    }

    @ExceptionHandler(BadRequestException.class)
    public ProblemDetail handleInvalidUrl(BadRequestException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(BAD_REQUEST, e.getMessage());
        problemDetail.setTitle("Bad Request");
        return problemDetail;
    }
}