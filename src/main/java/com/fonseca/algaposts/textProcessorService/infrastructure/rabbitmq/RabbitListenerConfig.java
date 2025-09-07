package com.fonseca.algaposts.textProcessorService.infrastructure.rabbitmq;

import com.fonseca.algaposts.textProcessorService.api.model.TextProcessingMessageRequest;
import com.fonseca.algaposts.textProcessorService.api.service.TextProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitListenerConfig {

    private final TextProcessingService textProcessingService;

    @RabbitListener(queues = RabbitConfig.POST_QUEUE)
    public void consumeProcessingMessage(@Payload TextProcessingMessageRequest request) {
        textProcessingService.receiveMessage(request);
    }

}
