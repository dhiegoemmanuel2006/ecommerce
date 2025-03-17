package com.dhiegoCommerce.EcommerceProject.services;

import com.dhiegoCommerce.EcommerceProject.dtos.ProductDTO;
import com.dhiegoCommerce.EcommerceProject.entities.Product;
import com.dhiegoCommerce.EcommerceProject.repositories.ProductRepository;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
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
            Map<String, String> mensagem = Map.of("resultado", "Produto cadastrado com sucesso!");
            return new ResponseEntity<>(mensagem, HttpStatus.CREATED);
        }
    }


    public List<Product> findAllProductsTable() {
        List<Product> list = pRepository.findAll();

        if(list != null && !list.isEmpty()){
            return list;
        } else {
            Map<String, String> message = Map.of("mensagem", "Nenhum produto encontrado");
            return null;
        }
    }

    public ResponseEntity findAllProducts() {
        List<Product> list = pRepository.findAll();

        if(list != null && !list.isEmpty()){
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            Map<String, String> message = Map.of("mensagem", "Nenhum produto encontrado");
            return new ResponseEntity<>(message,HttpStatus.NO_CONTENT);
        }
    }



    public ResponseEntity deleteProductById(Long id) {
        if(pRepository.existsById(id)){
            pRepository.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);
        } else{
            Map<String, String> erro = Map.of("erro","O sistema não conseguiu encontrar produto com este id");

            return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity filtWithName(String name){
        if(name == null || name.isEmpty()){
            Map<String, String> erro = Map.of("erro", "Nome inserido é inválido!");
            return new ResponseEntity<>(erro, HttpStatus.BAD_REQUEST);
        }

        String searchName = name.trim().toLowerCase();
        List<Product> res = pRepository.searchByNameFlexible(searchName);

        if(!res.isEmpty()){
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            Map<String, String> result = Map.of("resultado", "Nome não encontrado!");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity updateProduct (Long id, ProductDTO arg) {
        if(!pRepository.existsById(id) || arg == null){
            Map<String, String> erro = Map.of("erro", "Algum dos dados do usuário não está corretamente inserido!");
            return new ResponseEntity<>(erro, HttpStatus.BAD_REQUEST);
        }

        List<String> erros = new ArrayList<>();
        if(arg.getQuantity() < 0){
            erros.add("A quantidade de produtos não pode ser negativa");
        }
        if(arg.getPrice() < 0){
            erros.add("O valor do produto não pode ser negativo");
        }

        if(!erros.isEmpty()){
            return new ResponseEntity<>(erros, HttpStatus.BAD_REQUEST);
        }
        Product req = pRepository.findById(id).get();


        if(arg.getName() != null && !arg.getName().isEmpty()){
            req.setName(arg.getName());
        }
        if(arg.getDescription() != null && !arg.getDescription().isEmpty()){
            req.setDescription(arg.getDescription());
        }
        if(arg.getPrice() > 0){
            req.setPrice(arg.getPrice());
        }
        if(arg.getQuantity() < 0){
            req.setQuantity(arg.getQuantity());
        }
        pRepository.save(req);
        Map<String, String> message = Map.of("mensagem", "Produto atualizado com sucesso!");
        return new ResponseEntity<>(HttpStatus.OK);
    }





}
