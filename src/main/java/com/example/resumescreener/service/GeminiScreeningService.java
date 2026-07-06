package com.example.resumescreener.service;

import com.example.resumescreener.exception.ScreeningException;
import com.example.resumescreener.model.GeminiRequest;
import com.example.resumescreener.model.GeminiResponse;
import com.example.resumescreener.model.ScreeningResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.ArrayList;
import java.util.List;

@Service("gemini")
public class GeminiScreeningService implements AiScreeningService {

    private final RestClient restClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GeminiScreeningService(@Qualifier("geminiRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public ScreeningResult screen(String resumeText, String jobDescription) {
        try {
            GeminiRequest request = new GeminiRequest(buildPrompt(resumeText, jobDescription));

            GeminiResponse response = restClient.post()
                    .body(request)
                    .retrieve()
                    .body(GeminiResponse.class);

            String rawJson = response.candidates()
                    .stream()
                    .findFirst()
                    .map(c -> c.content().parts().get(0).text())
                    .orElseThrow(() -> new ScreeningException("No response from Gemini"));

            return parseResult(rawJson);

        } catch (ScreeningException e) {
            throw e;
        } catch (RestClientResponseException e) {
            throw new ScreeningException("Gemini API error: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            throw new ScreeningException("Screening failed: " + e.getMessage());
        }
    }

    private String buildPrompt(String resumeText, String jobDescription) {
        return """
                You are an expert HR recruiter. Analyze the resume against the job description and respond ONLY with a JSON object — no explanation, no markdown, no extra text.
                
                Resume:
                %s
                
                Job Description:
                %s
                
                Respond with exactly this JSON structure:
                {
                    "matchScore": <number 0-100>,
                    "strengths": ["<strength1>", "<strength2>", "<strength3>"],
                    "missingSkills": ["<skill1>", "<skill2>"],
                    "recommendation": "<STRONG_MATCH or GOOD_MATCH or WEAK_MATCH>",
                    "summary": "<2-3 sentence summary>"
                }
                """.formatted(resumeText, jobDescription);
    }

    private ScreeningResult parseResult(String rawJson) {
        try {
            String clean = rawJson
                    .replaceAll("```json", "")
                    .replaceAll("```", "")
                    .trim();

            JsonNode node = objectMapper.readTree(clean);

            int matchScore = node.get("matchScore").asInt();
            String recommendation = node.get("recommendation").asText();
            String summary = node.get("summary").asText();

            List<String> strengths = new ArrayList<>();
            node.get("strengths").forEach(s -> strengths.add(s.asText()));

            List<String> missingSkills = new ArrayList<>();
            node.get("missingSkills").forEach(s -> missingSkills.add(s.asText()));

            return new ScreeningResult(matchScore, strengths, missingSkills, recommendation, summary);

        } catch (Exception e) {
            throw new ScreeningException("Failed to parse Gemini response: " + e.getMessage());
        }
    }
}
