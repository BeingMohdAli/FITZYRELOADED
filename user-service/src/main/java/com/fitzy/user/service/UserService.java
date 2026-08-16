package com.fitzy.user.service;

import com.fitzy.common.exception.ResourceNotFoundException;
import com.fitzy.user.dto.UserRequest;
import com.fitzy.user.dto.UserResponse;
import com.fitzy.user.entity.User;
import com.fitzy.user.mapper.UserMapper;
import com.fitzy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse createUser(UserRequest userRequest, String userId) {

        UUID keycloakId;

        try {
            keycloakId = UUID.fromString(userId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Invalid Keycloak user ID: " + userId
            );
        }

        // User already exists with this Keycloak ID
        var existingUser = userRepository.findById(keycloakId);

        if (existingUser.isPresent()) {
            return userMapper.toResponse(existingUser.get());
        }

        // User with this email already exists
        var existingByEmail =
                userRepository.findByEmail(userRequest.getEmail());

        if (existingByEmail.isPresent()) {
            return userMapper.toResponse(existingByEmail.get());
        }

        User user = userMapper.toEntity(userRequest);

        user.setId(keycloakId);

        User savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }

    public UserResponse getUserById(UUID id) {

        User user = userRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id " + id
                        )
                );

        return userMapper.toResponse(user);
    }
}