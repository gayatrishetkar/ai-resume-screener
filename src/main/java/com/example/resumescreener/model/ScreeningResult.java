package com.example.resumescreener.model;

import java.util.List;

public record ScreeningResult(

        int matchScore,
        List<String> strengths,
        List<String> missingSkills,
        String recommendation,
        String summarization
) {
}
