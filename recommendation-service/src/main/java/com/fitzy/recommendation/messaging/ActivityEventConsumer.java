package com.fitzy.recommendation.messaging;

import com.fitzy.common.event.ActivityTrackedEvent;
import com.fitzy.common.messaging.RabbitMQConstants;
import com.fitzy.recommendation.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ActivityEventConsumer {

    private final RecommendationService recommendationService;

    @RabbitListener(queues = RabbitMQConstants.ACTIVITY_TRACKED_QUEUE)
    public void consume(ActivityTrackedEvent event) {
        log.info("Received ActivityTrackedEvent {} for activity {}", event.eventId(), event.activityId());
        try {
            recommendationService.generateAndSaveRecommendation(event);
        } catch (Exception e) {
            // Without this try/catch, an exception here (e.g. Gemini down, GeminiClient
            // throwing ExternalServiceException) causes RabbitMQ to requeue the message by
            // default — which becomes an infinite retry loop hammering Gemini's API.
            // For now: log and drop. A more production-grade version would route failed
            // messages to a dead-letter exchange for inspection instead of discarding them.
            log.error("Failed to generate recommendation for activity {}", event.activityId(), e);
        }
    }
}