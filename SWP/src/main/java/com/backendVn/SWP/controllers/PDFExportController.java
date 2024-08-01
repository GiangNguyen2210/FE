
package com.backendVn.SWP.controllers;

import com.backendVn.SWP.services.PDFGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@RestController
public class PDFExportController {

    private static final Logger logger = LoggerFactory.getLogger(PDFExportController.class);

    @Autowired
    private PDFGeneratorService pdfGeneratorService;

    @GetMapping("/generate-certificate/{requestId}")
    public ResponseEntity<byte[]> generateCertificate(@PathVariable Integer requestId) {
        String templatePath = "media/PDFForm.pdf"; // Absolute path to the template

        try {
            // Use a temporary file for the output
            Path tempFile = Files.createTempFile("GeneratedWarrantyCertificate", ".pdf");

            // Generate the PDF
            pdfGeneratorService.generateWarrantyCertificate(templatePath, tempFile.toString(), requestId);

            // Read the generated PDF file
            byte[] contents;
            try (var inputStream = Files.newInputStream(tempFile)) {
                contents = inputStream.readAllBytes();
            }

            // Clean up the temporary file
            Files.deleteIfExists(tempFile);

            // Return the PDF as a downloadable file
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=GeneratedWarrantyCertificate.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(contents);
        } catch (FileNotFoundException e) {
            logger.error("Template file not found: {}", templatePath, e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Template file not found: " + templatePath, e);
        } catch (Exception e) {
            logger.error("Error generating PDF for requestId: {}", requestId, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error generating PDF", e);
        }
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException e) {
        return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
    }
}

