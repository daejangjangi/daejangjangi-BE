package com.daejangjangi.backend.like.repository;

import com.daejangjangi.backend.like.domain.entity.ProductLike;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.product.domain.entity.Product;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductLikeRepository extends JpaRepository<ProductLike, Long> {

  Optional<ProductLike> findByMemberAndProduct(Member member, Product product);

  @Query("SELECT pl FROM ProductLike pl JOIN FETCH pl.product WHERE pl.member = :member")
  Page<ProductLike> findByMember(@Param("member") Member member, Pageable pageable);
}
