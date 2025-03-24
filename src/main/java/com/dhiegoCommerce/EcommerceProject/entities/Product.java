package com.dhiegoCommerce.EcommerceProject.entities;

import com.dhiegoCommerce.EcommerceProject.dtos.ProductDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@AllArgsConstructor
@Entity
@Table(name = "produtos")
public class Product {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private double value;

    @Column(name = "quantidade")
    private Long quantity;

    @Column(name = "description")
    private String description;

    public Product(ProductDTO dto){
        this.name = dto.getName();
        this.value = dto.getPrice();
        this.quantity = dto.getQuantity();
        this.description = dto.getDescription();
    }
    public Product (){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return value;
    }

    public void setPrice(double price) {
        this.value = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
