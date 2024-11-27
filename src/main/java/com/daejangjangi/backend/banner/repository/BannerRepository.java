package com.daejangjangi.backend.banner.repository;

import com.daejangjangi.backend.banner.domain.entity.Banner;
import com.daejangjangi.backend.product.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepository extends JpaRepository<Banner, Long> {

  boolean existsByProduct(Product product);
}
