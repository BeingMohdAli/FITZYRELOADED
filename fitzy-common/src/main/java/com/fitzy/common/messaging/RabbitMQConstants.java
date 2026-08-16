package com.fitzy.common.messaging;

public final class RabbitMQConstants {
    public static final String ACTIVITY_EXCHANGE = "activity.exchange";
    public static final String ACTIVITY_TRACKED_QUEUE = "activity.tracked.queue";
    public static final String ACTIVITY_TRACKED_ROUTING_KEY = "activity.tracked";

    private RabbitMQConstants() {} // never instantiated — just a namespace for constants
}