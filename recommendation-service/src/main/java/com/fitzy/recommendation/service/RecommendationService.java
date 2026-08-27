package com.fitzy.recommendation.service;

import com.fitzy.common.event.ActivityTrackedEvent;
import com.fitzy.common.exception.ExternalServiceException;
import com.fitzy.common.exception.ResourceNotFoundException;
import com.fitzy.recommendation.client.GeminiClient;
import com.fitzy.recommendation.client.GeminiGeneratedContent;
import com.fitzy.recommendation.dto.RecommendationResponse;
import com.fitzy.recommendation.entity.Recommendation;
import com.fitzy.recommendation.mapper.RecommendationMapper;
import com.fitzy.recommendation.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendationService {

    private final GeminiClient geminiClient;
    private final RecommendationRepository recommendationRepository;
    private final RecommendationMapper recommendationMapper;

    public void generateAndSaveRecommendation(ActivityTrackedEvent event) {
        log.info("Generating recommendation for activity {}", event.activityId());

        GeminiGeneratedContent content;
        try {
            content = geminiClient.generateRecommendation(event);
        } catch (ExternalServiceException e) {
            log.error("Gemini unavailable for activity {} — saving a fallback recommendation instead", event.activityId(), e);
            content = fallbackContent(event);
        }

        Recommendation recommendation = new Recommendation();
        recommendation.setActivityId(event.activityId());
        recommendation.setUserId(event.userId());
        recommendation.setSummary(content.summary());
        recommendation.setImprovements(content.improvements());
        recommendation.setSuggestions(content.suggestions());
        recommendation.setSafetyTips(content.safetyTips());
        recommendation.setCreatedAt(Instant.now());

        Recommendation saved = recommendationRepository.save(recommendation);
        log.info("Saved recommendation {} for activity {}", saved.getId(), saved.getActivityId());

        recommendationMapper.toResponse(saved);
    }

    private GeminiGeneratedContent fallbackContent(ActivityTrackedEvent event) {
        return new GeminiGeneratedContent(
                "Logged your " + event.activityType().toLowerCase().replace("_", " ") + " session — "
                        + event.durationMinutes() + " min, " + event.caloriesBurnt() + " cal. "
                        + "Our AI coach couldn't reach its analysis engine this time, so here are some general pointers instead.",
                List.of("Warm up for 5 minutes before higher-intensity effort", "Cool down and stretch once you're done"),
                List.of("Aim to repeat this activity a couple more times this week for consistency", "Note how you felt afterward to spot patterns over time"),
                List.of("Stay hydrated throughout", "Stop and rest if you feel sharp pain or dizziness")
        );
    }

    public RecommendationResponse getRecommendationByActivityId(UUID activityId) {
        Recommendation recommendation = recommendationRepository.findByActivityId(activityId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No recommendation found for activity " + activityId));
        return recommendationMapper.toResponse(recommendation);
    }

    public Page<RecommendationResponse> getRecommendationsForUser(String userId, Pageable pageable) {
        Page<Recommendation> recommendations = recommendationRepository.findByUserId(userId, pageable);
        return recommendations.map(
                r->recommendationMapper.toResponse(r));
    }
}