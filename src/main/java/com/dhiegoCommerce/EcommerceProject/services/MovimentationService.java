package com.dhiegoCommerce.EcommerceProject.services;

import com.dhiegoCommerce.EcommerceProject.dtos.MovimentationDTO;
import com.dhiegoCommerce.EcommerceProject.entities.Movimentation;
import com.dhiegoCommerce.EcommerceProject.entities.Product;
import com.dhiegoCommerce.EcommerceProject.exceptions.QuantityInvalidException;
import com.dhiegoCommerce.EcommerceProject.repositories.MovimentationRepository;
import com.dhiegoCommerce.EcommerceProject.repositories.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class MovimentationService {
    private final MovimentationRepository mRepository;
    private final ProductRepository pRepository;

    public MovimentationService(MovimentationRepository mRepository, ProductService pService, ProductRepository pRepository) {
        this.mRepository = mRepository;
        this.pRepository = pRepository;
    }

    public ResponseEntity entry(MovimentationDTO dto) {
       Product p = pRepository.findById(dto.getIdDoProdutos()).get();

       if(p != null){
           Long reserva = p.getQuantity();
           p.setQuantity(p.getQuantity() + dto.getQuantityOfProduct());
           if(p.getQuantity() < 0){
               return ResponseEntity.badRequest().body(new QuantityInvalidException("Quantidade dos produtos não pode ser negativa"));
           }

           Movimentation movimentation = new Movimentation();
           movimentation.setNomeProduto(p.getName());
           movimentation.setQuantityOfProduct(dto.getQuantityOfProduct());
           movimentation.setHorarioOperacao(LocalDateTime.now());
           mRepository.save(movimentation);

           return new ResponseEntity<>(HttpStatus.CREATED);
       }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public List<Movimentation> getAll() {
        return mRepository.findAll();
    }
}
