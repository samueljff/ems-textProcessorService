package com.fonseca.algaposts.textProcessorService.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TextProcessingMessageRequest {
    private UUID postId;
    private String postBody;
}