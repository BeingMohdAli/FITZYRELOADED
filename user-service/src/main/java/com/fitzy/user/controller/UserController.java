package com.fitzy.user.controller;

import com.fitzy.common.exception.ForbiddenOperationException;
import com.fitzy.user.dto.UserRequest;
import com.fitzy.user.dto.UserResponse;
import com.fitzy.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request,
                                                   @AuthenticationPrincipal Jwt jwt){
        UserResponse user = userService.createUser(request, jwt.getSubject());
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable UUID id,
                                                    @AuthenticationPrincipal Jwt jwt){
        if (!id.toString().equals(jwt.getSubject())) {
            throw new ForbiddenOperationException("You can only access your own profile");
        }
        UserResponse fetchedUserById = userService.getUserById(id);
        return ResponseEntity.ok(fetchedUserById);
    }


}
