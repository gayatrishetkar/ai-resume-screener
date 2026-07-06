package com.example.resumescreener.model;

import java.util.List;

public record ClaudeResponse(
        List<Content> content
) {
    public record Content(
            String type,
            String text
    ) {}
}
