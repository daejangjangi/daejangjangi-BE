package com.daejangjangi.backend.product.repository;

import com.daejangjangi.backend.product.domain.entity.ProductDisease;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDiseaseRepository extends JpaRepository<ProductDisease, Long> {

}
