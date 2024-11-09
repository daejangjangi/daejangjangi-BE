package com.daejangjangi.backend.product.repository;

import com.daejangjangi.backend.product.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
