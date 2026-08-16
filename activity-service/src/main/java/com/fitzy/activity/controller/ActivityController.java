package com.fitzy.activity.controller;

import com.fitzy.activity.dto.ActivityRequest;
import com.fitzy.activity.dto.ActivityResponse;
import com.fitzy.activity.service.ActivityService;
import com.fitzy.common.exception.ExternalServiceException;
import com.fitzy.common.exception.ForbiddenOperationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

// TODO: @RestController @RequestMapping("/api/v1/activities") — POST, GET by id, GET paginated list
@RestController
@RequestMapping("/api/v1/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @PostMapping
    public ResponseEntity<ActivityResponse> trackActivity(
            @Valid @RequestBody ActivityRequest activityRequest,
            @AuthenticationPrincipal Jwt jwt) {
        ActivityResponse activity = activityService.trackActivity(activityRequest, jwt.getSubject());
        return ResponseEntity.status(HttpStatus.CREATED).body(activity);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ActivityResponse> getActivityById(
            @PathVariable UUID id,
            @AuthenticationPrincipal Jwt jwt) {
        ActivityResponse activity = activityService.getActivityById(id);
        if (!activity.getUserId().equals(jwt.getSubject()))
            throw new ForbiddenOperationException("You do not have access to this activity");

        return ResponseEntity.ok(activity);
    }

    @GetMapping
    public ResponseEntity<Page<ActivityResponse>> getActivitiesForUser(
            @AuthenticationPrincipal Jwt jwt,
            Pageable pageable) {
        Page<ActivityResponse> activitiesForUser = activityService.getActivitiesForUser(jwt.getSubject(), pageable);
        return ResponseEntity.ok(activitiesForUser);
    }
}
