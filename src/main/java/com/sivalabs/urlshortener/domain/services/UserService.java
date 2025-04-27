package com.sivalabs.urlshortener.domain.services;

import com.sivalabs.urlshortener.domain.entities.User;
import com.sivalabs.urlshortener.domain.models.CreateUserCmd;
import com.sivalabs.urlshortener.domain.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.sivalabs.urlshortener.domain.models.Role.ROLE_USER;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void createUser(CreateUserCmd cmd) {
        if (userRepository.existsByEmail(cmd.email())) {
            throw new RuntimeException("Email already exists");
        }
        var user = new User();
        user.setEmail(cmd.email());
        user.setName(cmd.name());
        user.setPassword(passwordEncoder.encode(cmd.password()));
        user.setRole(cmd.role() != null ? cmd.role() : ROLE_USER);
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    public Optional<User> login(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()));
    }
}