package com.dhiegoCommerce.EcommerceProject.controllers;

import com.dhiegoCommerce.EcommerceProject.dtos.MovimentationDTO;
import com.dhiegoCommerce.EcommerceProject.entities.Movimentation;
import com.dhiegoCommerce.EcommerceProject.services.MovimentationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/movimentation")
public class MovimentationsController {
    private final MovimentationService movimentationService;
    public MovimentationsController(MovimentationService movimentationService) {
        this.movimentationService = movimentationService;
    }

    @PostMapping
    public ResponseEntity entry(@RequestBody MovimentationDTO movimentation) {
        return movimentationService.entry(movimentation);
    }
}
