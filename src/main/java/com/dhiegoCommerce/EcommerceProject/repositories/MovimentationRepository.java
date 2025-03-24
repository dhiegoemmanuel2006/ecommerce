package com.dhiegoCommerce.EcommerceProject.repositories;

import com.dhiegoCommerce.EcommerceProject.entities.Movimentation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentationRepository extends JpaRepository<Movimentation, Long> {
}
