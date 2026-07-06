package com.example.resumescreener.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${anthropic.api.url}")
    private String BASE_URL;

    @Value("${anthropic.api.key}")
    private String API_KEY;

    @Value("${openai.api.url}")
    private String BASE_URL_OPENAI;

    @Value("${openai.api.key}")
    private String API_KEY_OPENAI;

    @Value("${gemini.api.url}")
    private String BASE_URL_GEMINI;

    @Value("${gemini.api.key}")
    private String API_KEY_GEMINI;

    @Value("${gemini.model}")
    private String geminiModel;

    @Bean
    RestClient anthropicRestClient() {
        return RestClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader("x-api-key", API_KEY)
                .defaultHeader("anthropic-version", "2023-06-01")
                .defaultHeader("Content-Type", "application/json")
                .defaultStatusHandler(HttpStatusCode::is5xxServerError, ((request, response) ->{
                    throw new RuntimeException("Server exception occurred!!");
                }))
                .build();
    }

    @Bean
    RestClient openAiRestClient() {
        return RestClient.builder()
                .baseUrl(BASE_URL_OPENAI)
                .defaultHeader("Authorization", "Bearer " + API_KEY_OPENAI)
                .defaultHeader("Content-Type", "application/json")
                .defaultStatusHandler(HttpStatusCode::is5xxServerError, ((request, response) ->{
                    throw new RuntimeException("Server exception occurred!!");
                }))
                .build();
    }

    @Bean
    RestClient geminiRestClient() {
        return RestClient.builder()
                .baseUrl(BASE_URL_GEMINI + geminiModel + ":generateContent?key=" + API_KEY_GEMINI)
                .defaultHeader("Content-Type", "application/json")
                .defaultStatusHandler(HttpStatusCode::is5xxServerError, (request, response) -> {
                    throw new RuntimeException("Server exception occurred!!");
                })
                .build();
    }
}
