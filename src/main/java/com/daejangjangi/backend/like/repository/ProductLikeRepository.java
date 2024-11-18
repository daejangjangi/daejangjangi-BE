package com.daejangjangi.backend.like.repository;

import com.daejangjangi.backend.like.domain.entity.ProductLike;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.product.domain.entity.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductLikeRepository extends JpaRepository<ProductLike, Long> {

  Optional<ProductLike> findByMemberAndProduct(Member member, Product product);
}
