package com.example.resumescreener.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.logging.log4j.message.Message;

import java.util.List;

public record ClaudeRequest(
        String model,
        @JsonProperty("max_tokens") int maxTokens,
        List<Message> messages
) {
    public ClaudeRequest(String model, int maxTokens, String userMessage) {
        this(model, maxTokens, List.of(new Message("user", userMessage)));
    }

    public record Message(
            String role,
            String content
    ) {}
}
