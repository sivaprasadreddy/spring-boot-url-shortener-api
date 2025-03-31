package com.sivalabs.urlshortener.web.dto;

import com.sivalabs.urlshortener.domain.model.Role;

public record RegistrationResponse(String email, String name, Role role) {}