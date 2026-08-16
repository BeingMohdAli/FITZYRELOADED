package com.fitzy.activity.service;

import com.fitzy.activity.dto.ActivityRequest;
import com.fitzy.activity.dto.ActivityResponse;
import com.fitzy.activity.entity.Activity;
import com.fitzy.activity.mapper.ActivityMapper;
import com.fitzy.activity.messaging.ActivityEventPublisher;
import com.fitzy.activity.repository.ActivityRepository;
import com.fitzy.common.event.ActivityTrackedEvent;
import com.fitzy.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

// TODO: @Service — orchestrates repository + mapper + ActivityEventPublisher
@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityMapper activityMapper;
    private final ActivityRepository activityRepository;

    private final ActivityEventPublisher activityEventPublisher; // add this field

    public ActivityResponse trackActivity(ActivityRequest request, String userId) {
        Activity activity = activityMapper.toEntity(request);
        activity.setUserId(userId);
        activity.setCreatedAt(Instant.now());
        Activity savedActivity = activityRepository.save(activity);

        ActivityTrackedEvent event = ActivityTrackedEvent.of(
                savedActivity.getId(),
                savedActivity.getUserId(),
                savedActivity.getActivity().name(),   // enum -> plain String for the event
                savedActivity.getDurationMinutes(),
                savedActivity.getCaloriesBurnt(),
                savedActivity.getStartTime(),
                savedActivity.getAdditionalMetrics()
        );
        activityEventPublisher.publish(event);

        return activityMapper.toResponse(savedActivity);
    }

    public ActivityResponse getActivityById(UUID id){
        Activity activity = activityRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No activity with this id " + id + " is found"));
        return activityMapper.toResponse(activity);
    }

    public Page<ActivityResponse> getActivitiesForUser(String userId, Pageable pageable) {
        Page<Activity> activities = activityRepository.findByUserId(userId, pageable);
        return activities.map(activityMapper::toResponse);
    }

}
