package com.dhiegoCommerce.EcommerceProject.controllers;

import com.dhiegoCommerce.EcommerceProject.services.GenerateReportService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
public class GeneratePdfController {
    GenerateReportService grService;
    public GeneratePdfController(GenerateReportService grService) {
        this.grService = grService;
    }


    @GetMapping("/gerarpdf/product")
    public void generatePdfForProduct(HttpServletResponse response) throws IOException {
        grService.generateReport(response);
    }

    @GetMapping
    public void generatePdfForMovimentation(HttpServletResponse response) throws IOException {
        grService.generateReportForMovimentation(response);
    }
}
