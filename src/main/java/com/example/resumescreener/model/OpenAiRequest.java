package com.example.resumescreener.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record OpenAiRequest(
        String model,
        @JsonProperty("max_tokens") int maxTokens,
        List<Message> messages
) {
    public OpenAiRequest(String model, int maxTokens, String userMessage) {
        this(model, maxTokens, List.of(new Message("user", userMessage)));
    }

    public record Message(
            String role,
            String content
    ) {}
}
