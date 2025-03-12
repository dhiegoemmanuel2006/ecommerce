package com.dhiegoCommerce.EcommerceProject.services;

import com.dhiegoCommerce.EcommerceProject.dtos.ProductDTO;
import com.dhiegoCommerce.EcommerceProject.entities.Product;
import com.dhiegoCommerce.EcommerceProject.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository pRepository;

    public ResponseEntity<?> addProduct(ProductDTO arg) {
        List<String> erros = new ArrayList<>();
        if (arg.getName() == null) {
            erros.add("O nome não pode ser nulo");
        }
        if (arg.getDescription() == null) {
            erros.add("A descrição não pode ser nula");
        }
        if(arg.getPrice() < 0){
            erros.add("O valor do produto deve ser acima de 0.00");
        }
        if(arg.getQuantity() < 0){
            erros.add("O produto não pode ser inicializado no sistema com estoque negativo");
        }

        if(!erros.isEmpty()){
            return new ResponseEntity<>(erros, HttpStatus.BAD_REQUEST);
        } else{
            Product req = new Product (arg);
            pRepository.save(req);
            String mensage = "Produto cadastrado com sucesso!";
            return new ResponseEntity<>(mensage, HttpStatus.CREATED);
        }
    }


    public ResponseEntity findAllProducts() {
        List<Product> list = pRepository.findAll();

        if(list != null){
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            String message = "Sistema não conseguiu captar a lista de produtos";
            return new ResponseEntity<>(message,HttpStatus.NO_CONTENT);
        }
    }



    public ResponseEntity deleteProductById(Long id) {
        if(pRepository.existsById(id)){
            pRepository.deleteById(id);
            return new ResponseEntity("Produto excluido com sucesso",HttpStatus.OK);
        } else{
            return new ResponseEntity<>("O sistema não conseguiu encontrar produto com este id", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity filtWithName(String name){
        if(name == null || name.isEmpty()){
            String message = "O nome inserido é inválido";
            return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
        }

        String searchName = name.trim().toLowerCase();
        List<Product> res = pRepository.searchByNameFlexible(searchName);

        if(!res.isEmpty()){
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            String message = "Nome não encontrado!";
            return new ResponseEntity<>(message,HttpStatus.NOT_FOUND);
        }
    }





}
