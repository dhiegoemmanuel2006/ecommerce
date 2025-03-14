package com.dhiegoCommerce.EcommerceProject.controllers;


import com.dhiegoCommerce.EcommerceProject.entities.Product;
import com.dhiegoCommerce.EcommerceProject.repositories.ProductRepository;
import com.dhiegoCommerce.EcommerceProject.services.JasperReportService;
import com.dhiegoCommerce.EcommerceProject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.List;

@RestController("/relatorio")
public class JasperController {

    private final JasperReportService jrService;
    private ProductRepository pRepository;

    public JasperController(JasperReportService jrService, ProductRepository pRepository) {
        this.jrService = jrService;
        this.pRepository = pRepository;
    }

    @PostMapping("/gerar-pdf")
    public void gerarRelatorioPDF() throws FileNotFoundException {
        List<Product> products = pRepository.findAll();
        jrService.gerarRelatorio(products);
    }
}
