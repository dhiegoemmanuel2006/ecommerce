package com.dhiegoCommerce.EcommerceProject.dtos;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.Date;


public class MovimentationDTO {
    private Long idDoProdutos;
    private Long quantityOfProduct;

    public Long getIdDoProdutos() {
        return idDoProdutos;
    }

    public void setIdDoProdutos(Long idDoProdutos) {
        this.idDoProdutos = idDoProdutos;
    }

    public Long getQuantityOfProduct() {
        return quantityOfProduct;
    }

    public void setQuantityOfProduct(Long quantityOfProduct) {
        this.quantityOfProduct = quantityOfProduct;
    }
}
