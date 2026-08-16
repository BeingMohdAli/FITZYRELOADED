package com.fitzy.common.event;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

/**
 * Published by activity-service the moment an activity is saved.
 * Consumed by recommendation-service to generate an AI recommendation.
 *
 * Carries a full copy of the activity's data (not just activityId) because
 * recommendation-service has its own database and cannot query activity-service's
 * Postgres directly — this event is the only way the data crosses that boundary.
 */
public record ActivityTrackedEvent(
        UUID eventId,
        UUID activityId,
        String userId,
        String activityType,
        Integer durationMinutes,
        Integer caloriesBurnt,
        Instant startTime,
        Map<String, String> additionalMetrics
) {

    public static ActivityTrackedEvent of(UUID activityId, String userId, String activityType,
                                          Integer durationMinutes, Integer caloriesBurnt,
                                          Instant startTime, Map<String, String> additionalMetrics) {
        return new ActivityTrackedEvent(
                UUID.randomUUID(), // eventId — unique per publish, useful later for logging/idempotency (e.g. "did I already process this exact event?")
                activityId, userId, activityType, durationMinutes, caloriesBurnt, startTime, additionalMetrics
        );
    }
}