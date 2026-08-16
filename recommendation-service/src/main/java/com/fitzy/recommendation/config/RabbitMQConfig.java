package com.fitzy.recommendation.config;

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
        return new Queue(RabbitMQConstants.ACTIVITY_TRACKED_QUEUE, true);
    }

    @Bean
    public Binding activityTrackedBinding(Queue activityTrackedQueue, TopicExchange activityExchange) {
        return BindingBuilder.bind(activityTrackedQueue)
                .to(activityExchange)
                .with(RabbitMQConstants.ACTIVITY_TRACKED_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}