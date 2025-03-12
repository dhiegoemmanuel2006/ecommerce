package com.dhiegoCommerce.EcommerceProject.controllers;

import com.dhiegoCommerce.EcommerceProject.dtos.ProductDTO;
import com.dhiegoCommerce.EcommerceProject.entities.Product;
import com.dhiegoCommerce.EcommerceProject.repositories.ProductRepository;
import com.dhiegoCommerce.EcommerceProject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private final ProductService pService;

    public ProductController(ProductService pService) {
        this.pService = pService;

    }

    @PostMapping("/product/add")
    public ResponseEntity<?> addProduct(@RequestBody ProductDTO arg) {
        return pService.addProduct(arg);
    }


    @GetMapping("/product/list")
    public ResponseEntity list() {
        return pService.findAllProducts();
    }

    @DeleteMapping("/product/remove/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        return pService.deleteProductById(id);
    }

    @GetMapping("/product/filt/{name}")
    public ResponseEntity findAllWithName(@PathVariable String name) {
        return pService.filtWithName(name);
    }



}
