package com.daejangjangi.backend.product.repository;

import com.daejangjangi.backend.product.domain.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

}
