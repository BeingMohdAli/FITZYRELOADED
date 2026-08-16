package com.fitzy.activity.messaging;

import com.fitzy.common.event.ActivityTrackedEvent;
import com.fitzy.common.messaging.RabbitMQConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publish(ActivityTrackedEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConstants.ACTIVITY_EXCHANGE,
                RabbitMQConstants.ACTIVITY_TRACKED_ROUTING_KEY,
                event
        );
        log.info("Published ActivityTrackedEvent {} for activity {}", event.eventId(), event.activityId());
    }
}