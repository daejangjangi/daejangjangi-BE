package com.daejangjangi.backend.product.repository;

import com.daejangjangi.backend.product.domain.entity.Discount;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Long> {

  Optional<Discount> findByName(String name);
}
