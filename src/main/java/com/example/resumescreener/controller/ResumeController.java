package com.example.resumescreener.controller;

import com.example.resumescreener.model.ScreeningResult;
import com.example.resumescreener.service.AiScreeningService;
import com.example.resumescreener.service.AnthropicScreeningService;
import com.example.resumescreener.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @PostMapping(value = "/screen", consumes = "multipart/form-data")
    public ResponseEntity<?> screen(
            @RequestParam("resume") MultipartFile resume,
            @RequestParam("jobDescription") String jobDescription,
            @RequestParam(value = "provider", defaultValue = "openai") String provider) {

        ScreeningResult result = resumeService.screen(resume, jobDescription, provider);
        return ResponseEntity.ok(result);
    }
}
