package com.fitzy.activity.config;

import com.fitzy.common.messaging.RabbitMQConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public TopicExchange activityExchange() {
        return new TopicExchange(RabbitMQConstants.ACTIVITY_EXCHANGE);
    }

    @Bean
    public Queue activityTrackedQueue() {
        return new Queue(RabbitMQConstants.ACTIVITY_TRACKED_QUEUE, true); // durable = survives a broker restart
    }

    @Bean
    public Binding activityTrackedBinding(Queue activityTrackedQueue, TopicExchange activityExchange) {
        return BindingBuilder.bind(activityTrackedQueue)
                .to(activityExchange)
                .with(RabbitMQConstants.ACTIVITY_TRACKED_ROUTING_KEY);
    }

    // Without this, RabbitTemplate serializes objects using Java's native serialization
    // (unreadable binary, and requires the exact same class on both ends). This makes it use JSON instead.
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}