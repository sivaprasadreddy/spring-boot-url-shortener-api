package com.sivalabs.urlshortener.web.controller;

import com.sivalabs.urlshortener.domain.model.CreateUserCmd;
import com.sivalabs.urlshortener.domain.model.Role;
import com.sivalabs.urlshortener.domain.service.UserService;
import com.sivalabs.urlshortener.security.JwtTokenHelper;
import com.sivalabs.urlshortener.web.dto.LoginRequest;
import com.sivalabs.urlshortener.web.dto.LoginResponse;
import com.sivalabs.urlshortener.web.dto.RegistrationRequest;
import com.sivalabs.urlshortener.web.dto.RegistrationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api")
@Tag(name = "Users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final JwtTokenHelper jwtTokenHelper;

    public UserController(UserService userService, JwtTokenHelper jwtTokenHelper) {
        this.userService = userService;
        this.jwtTokenHelper = jwtTokenHelper;
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Returns successful authentication response"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials"),
    })
    LoginResponse login(@RequestBody @Valid LoginRequest req) {
        log.info("Login request for username: {}", req.email());
        var user = userService
                .login(req.email(), req.password())
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

        var jwtToken = jwtTokenHelper.generateToken(user);
        return new LoginResponse(
                jwtToken.token(),
                jwtToken.expiresAt(),
                user.getEmail(),
                user.getName(),
                user.getRole().name());
    }

    @PostMapping("/users")
    @Operation(summary = "Create user")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Created user successfully"),
    })
    ResponseEntity<RegistrationResponse> creatUser(@RequestBody @Valid RegistrationRequest req) {
        log.info("Registration request for email: {}", req.email());
        var cmd = new CreateUserCmd(
                req.email(), req.password(),
                req.name(), Role.ROLE_USER);
        userService.createUser(cmd);
        var response = new RegistrationResponse(
                req.email(), req.name(), Role.ROLE_USER);
        return ResponseEntity.status(CREATED.value()).body(response);
    }
}