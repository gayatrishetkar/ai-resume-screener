package com.example.resumescreener.service;

import com.example.resumescreener.exception.PdfExtractionException;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PdfExtractorService {
    public String extract(MultipartFile file) {

        // Validate file
        if (file == null || file.isEmpty()) {
            throw new PdfExtractionException("Resume file is empty");
        }

        if (!file.getOriginalFilename().endsWith(".pdf")) {
            throw new PdfExtractionException("Only PDF files are supported");
        }

        try (PDDocument document = Loader.loadPDF(file.getBytes())) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);

            if (text == null || text.isBlank()) {
                throw new PdfExtractionException("Could not extract text from PDF — it may be scanned or image-based");
            }

            return text.trim();

        } catch (PdfExtractionException e) {
            throw e;
        } catch (Exception e) {
            throw new PdfExtractionException("Failed to read PDF: " + e.getMessage());
        }
    }
}
