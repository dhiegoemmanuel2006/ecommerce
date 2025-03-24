package com.dhiegoCommerce.EcommerceProject.entities;

import com.dhiegoCommerce.EcommerceProject.dtos.MovimentationDTO;
import com.dhiegoCommerce.EcommerceProject.repositories.ProductRepository;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "operações")
public class Movimentation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_operacao")
    private Long id;

    @Column(name = "nome_produto")
    private String nomeProduto;

    @Column(name = "horario_operacao")
    private LocalDateTime horarioOperacao;

    @Column(name = "quantidade_do_produto")
    private Long quantityOfProduct;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public LocalDateTime getHorarioOperacao() {
        return horarioOperacao;
    }

    public void setHorarioOperacao(LocalDateTime horarioOperacao) {
        this.horarioOperacao = horarioOperacao;
    }

    public Long getQuantityOfProduct() {
        return quantityOfProduct;
    }

    public void setQuantityOfProduct(Long quantityOfProduct) {
        this.quantityOfProduct = quantityOfProduct;
    }
}
