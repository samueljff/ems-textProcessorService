package com.fonseca.algaposts.textProcessorService.api.service;

import com.fonseca.algaposts.textProcessorService.api.model.ProcessingResultMessage;
import com.fonseca.algaposts.textProcessorService.api.model.TextProcessingMessageRequest;
import com.fonseca.algaposts.textProcessorService.infrastructure.rabbitmq.RabbitConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Slf4j
public class TextProcessingService {

    private final AmqpTemplate amqpTemplate;

    public void receiveMessage(TextProcessingMessageRequest request) {
        log.info("Processando texto - PostID: {}", request.getPostId());

        int words = countWords(request.getPostBody());
        BigDecimal value = new BigDecimal(words * 0.10);

        log.info("Processamento concluído - PostID: {}, Palavras: {}, Valor: {}",
                request.getPostId(), words, value);

        ProcessingResultMessage result = new ProcessingResultMessage(request.getPostId(), words, value);
        amqpTemplate.convertAndSend(RabbitConfig.RESULT_QUEUE, result);
    }

    private int countWords(String body) {
        if (body == null || body.trim().isEmpty()) {
            return 0;
        }

        // Remove múltiplos espaços e quebras de linha, mantém apenas espaços simples
        String cleanText = body.trim().replaceAll("\\s+", " ");

        // Divide por espaços e conta apenas palavras não vazias
        return (int) Arrays.stream(cleanText.split(" "))
                .filter(word -> !word.trim().isEmpty())
                .count();
    }
}

