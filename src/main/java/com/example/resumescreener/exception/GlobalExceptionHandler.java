package com.example.resumescreener.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> handleMissingParams(MissingServletRequestParameterException ex) {
        return ResponseEntity.badRequest()
                .body(Map.of("error", "Missing required parameter: " + ex.getParameterName()));
    }

    @ExceptionHandler(PdfExtractionException.class)
    public ResponseEntity<?> handlePdfExtraction(PdfExtractionException ex) {
        return ResponseEntity.badRequest()
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(ScreeningException.class)
    public ResponseEntity<?> handleScreening(ScreeningException ex) {
        return ResponseEntity.badRequest()
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneral(Exception ex) {
        return ResponseEntity.internalServerError()
                .body(Map.of("error", "Something went wrong: " + ex.getMessage()));
    }
}
