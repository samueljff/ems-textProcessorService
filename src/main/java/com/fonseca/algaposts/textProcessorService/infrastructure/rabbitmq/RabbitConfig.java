package com.fonseca.algaposts.textProcessorService.infrastructure.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String POST_QUEUE = "text-processor-service.post-processing.v1.q";
    public static final String RESULT_QUEUE = "post-service.post-processing-result.v1.q";
    public static final String POST_DLQ = POST_QUEUE.replace(".q",".dlq");
    public static final String RESULT_DLQ = RESULT_QUEUE.replace(".q",".dlq");

    @Bean
    public Queue postQueue() {
        return QueueBuilder.durable(POST_QUEUE)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", POST_DLQ)
                .build();
    }

    @Bean
    public Queue resultQueue() {
        return QueueBuilder.durable(RESULT_QUEUE)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", RESULT_DLQ)
                .build();
    }

    @Bean
    public Queue postDlq() {
        return new Queue(POST_DLQ);
    }

    @Bean
    public Queue resultDlq() {
        return new Queue(RESULT_DLQ);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}