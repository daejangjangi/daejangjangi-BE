package com.daejangjangi.backend.product.repository;

import com.daejangjangi.backend.product.domain.entity.ProductGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductGroupRepository extends JpaRepository<ProductGroup, Long> {

}
