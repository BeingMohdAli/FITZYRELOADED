package com.fitzy.recommendation.controller;

import com.fitzy.common.exception.ForbiddenOperationException;
import com.fitzy.recommendation.dto.RecommendationResponse;
import com.fitzy.recommendation.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<RecommendationResponse> getRecommendationByActivityId(
            @PathVariable UUID activityId,
            @AuthenticationPrincipal Jwt jwt) {
        RecommendationResponse response = recommendationService.getRecommendationByActivityId(activityId);

        if (!response.getUserId().equals(jwt.getSubject())) {
            throw new ForbiddenOperationException("You do not have access to this recommendation");
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<RecommendationResponse>> getRecommendationsForUser(
            @AuthenticationPrincipal Jwt jwt,
            Pageable pageable) {
        Page<RecommendationResponse> recommendations = recommendationService.getRecommendationsForUser(jwt.getSubject(), pageable);
        return ResponseEntity.ok(recommendations);
    }
}