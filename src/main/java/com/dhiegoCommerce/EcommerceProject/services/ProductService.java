package com.dhiegoCommerce.EcommerceProject.services;

import com.dhiegoCommerce.EcommerceProject.dtos.ProductDTO;
import com.dhiegoCommerce.EcommerceProject.entities.Product;
import com.dhiegoCommerce.EcommerceProject.exceptions.*;
import com.dhiegoCommerce.EcommerceProject.repositories.ProductRepository;
import jakarta.xml.bind.ValidationException;
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
    private ProductRepository pRepository;

    ProductService(ProductRepository pRepository) {
        this.pRepository = pRepository;
    }

    public ResponseEntity addProduct(ProductDTO arg) {
        List<Exception> erros = new ArrayList<>();
        if (arg.getName() == null) {
            erros.add(new NullNameException("O nome não pode ser nulo"));
        }
        if (arg.getDescription() == null) {
            erros.add(new NullDescriptionException("A descrição não pode ser nula"));
        }
        if (arg.getPrice() < 0) {
            erros.add(new ValidationException("O valor do produto deve ser acima de 0.00"));
        }
        if (arg.getQuantity() < 0) {
            erros.add((new QuantityInvalidException("O estoque não pode ser negativo!")));
        }

        if (!erros.isEmpty()) {
            return new ResponseEntity<>(erros, HttpStatus.BAD_REQUEST);
        }
        Product req = new Product(arg);
        pRepository.save(req);
        Map<String, String> mensagem = Map.of("resultado", "Produto cadastrado com sucesso!");
        return new ResponseEntity<>(mensagem, HttpStatus.CREATED);
    }

    public Product findById(Long id) {
        return pRepository.findById(id).get();
    }

    public List<Product> findAllProductsTable() {
        List<Product> list = pRepository.findAll();

        if (list != null && !list.isEmpty()) {
            return list;
        } else {
            return null;
        }
    }

    public ResponseEntity findAllProducts() {
        List<Product> list = pRepository.findAll();

        if (list != null && !list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            Map<String, String> message = Map.of("mensagem", "Nenhum produto encontrado");
            return new ResponseEntity<>(message, HttpStatus.NO_CONTENT);
        }
    }


    public ResponseEntity deleteProductById(Long id) {
        if (pRepository.existsById(id)) {
            pRepository.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new IdNotFoundException("O sistema não conseguiu encontrar produto com o id inserido!"), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity filtWithName(String name) {
        if (name == null || name.isEmpty()) {
            return new ResponseEntity<>(new NameInvalidException("O nome inserido é inválido!"), HttpStatus.BAD_REQUEST);
        }

        String searchName = name.trim().toLowerCase();
        List<Product> res = pRepository.searchByNameFlexible(searchName);

        if (!res.isEmpty()) {
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {

            return new ResponseEntity<>(new NameNotFoundException("Nome não encontrado!"), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity updateProduct(Long id, ProductDTO arg) {
        if (!pRepository.existsById(id) || arg == null) {

            return new ResponseEntity<>(new InvalidDataOfUpdateException("Algum dado da requisição, está incorreto!"), HttpStatus.BAD_REQUEST);
        }

        List<Exception> erros = new ArrayList<>();
        if (arg.getQuantity() < 0) {
            erros.add(new QuantityInvalidException("O estoque não pode ser negativo!"));
        }
        if (arg.getPrice() < 0) {
            erros.add(new ValidationException("O valor do produto deve ser acima de 0.00"));
        }

        if (!erros.isEmpty()) {
            return new ResponseEntity<>(erros, HttpStatus.BAD_REQUEST);
        }
        Product req = pRepository.findById(id).get();


        if (arg.getName() != null && !arg.getName().isEmpty()) {
            req.setName(arg.getName());
        }
        if (arg.getDescription() != null && !arg.getDescription().isEmpty()) {
            req.setDescription(arg.getDescription());
        }
        if (arg.getPrice() > 0) {
            req.setPrice(arg.getPrice());
        }
        if (arg.getQuantity() < 0) {
            req.setQuantity(arg.getQuantity());
        }
        pRepository.save(req);
        Map<String, String> message = Map.of("mensagem", "Produto atualizado com sucesso!");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}



