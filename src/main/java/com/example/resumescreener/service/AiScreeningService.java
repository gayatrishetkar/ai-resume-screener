package com.example.resumescreener.service;

import com.example.resumescreener.model.ScreeningResult;
import org.springframework.web.multipart.MultipartFile;

public interface AiScreeningService {
    ScreeningResult screen(String resumeText, String jobDescription);
}
