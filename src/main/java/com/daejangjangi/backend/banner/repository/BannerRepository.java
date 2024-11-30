package com.daejangjangi.backend.banner.repository;

import com.daejangjangi.backend.banner.domain.entity.Banner;
import com.daejangjangi.backend.product.domain.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BannerRepository extends JpaRepository<Banner, Long> {

  boolean existsByProduct(Product product);

  @Query("SELECT b FROM Banner b JOIN FETCH b.product")
  List<Banner> findAllWithProduct();
}
