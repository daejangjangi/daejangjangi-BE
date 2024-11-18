package com.daejangjangi.backend.like.service;

import com.daejangjangi.backend.like.domain.entity.ProductLike;
import com.daejangjangi.backend.like.repository.ProductLikeRepository;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.product.domain.entity.Product;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductLikeService {

  private final ProductLikeRepository productLikeRepository;

  @Transactional
  public void like(Member member, Product product) {
    Optional<ProductLike> optionalProductLike =
        productLikeRepository.findByMemberAndProduct(member, product);
    if (optionalProductLike.isPresent()) {
      ProductLike productLike = optionalProductLike.get();
      productLikeRepository.delete(productLike);
    } else {
      ProductLike productLike = ProductLike.builder()
          .member(member)
          .product(product)
          .build();
      product.addLike(productLike);
      productLikeRepository.save(productLike);
    }
  }
}
