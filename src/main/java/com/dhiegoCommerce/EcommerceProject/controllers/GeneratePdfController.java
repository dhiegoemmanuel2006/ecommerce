package com.dhiegoCommerce.EcommerceProject.controllers;

import com.dhiegoCommerce.EcommerceProject.services.GenerateReportService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
public class GeneratePdfController {
    GenerateReportService grService;
    public GeneratePdfController(GenerateReportService grService) {
        this.grService = grService;
    }


    @PostMapping("/gerarpdf")
    public void generatePdf() throws FileNotFoundException {
        grService.generateReport();
    }
}
