package com.example.resumescreener.service;

import com.example.resumescreener.exception.ScreeningException;
import com.example.resumescreener.model.ScreeningResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class ResumeService {

    private final PdfExtractorService pdfExtractorService;
    private final Map<String, AiScreeningService> providers;

    public ResumeService(PdfExtractorService pdfExtractorService,
                         AnthropicScreeningService anthropicScreeningService,
                         OpenAiScreeningService openAiScreeningService,
                         GeminiScreeningService geminiScreeningService) {
        this.pdfExtractorService = pdfExtractorService;
        this.providers = Map.of(
                "anthropic", anthropicScreeningService,
                "openai", openAiScreeningService,
                "gemini", geminiScreeningService
        );
    }

    public ScreeningResult screen(MultipartFile resume, String jobDescription, String provider) {

        if (jobDescription == null || jobDescription.isBlank()) {
            throw new ScreeningException("Job description cannot be empty");
        }

        AiScreeningService service = providers.get(provider.toLowerCase());
        if (service == null) {
            throw new ScreeningException("Invalid provider: " + provider + ". Use 'openai' or 'anthropic'");
        }

        String resumeText = pdfExtractorService.extract(resume);
        return service.screen(resumeText, jobDescription);
    }
}
