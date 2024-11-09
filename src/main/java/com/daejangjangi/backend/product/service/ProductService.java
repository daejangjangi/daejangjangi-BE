package com.daejangjangi.backend.product.service;

import com.daejangjangi.backend.category.domain.Category;
import com.daejangjangi.backend.file.service.FileValidator;
import com.daejangjangi.backend.file.service.S3Manager;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.product.domain.dto.ProductResponseDto.RecommendedProduct;
import com.daejangjangi.backend.product.domain.entity.Product;
import com.daejangjangi.backend.product.domain.entity.ProductCategory;
import com.daejangjangi.backend.product.domain.mapper.ProductMapper;
import com.daejangjangi.backend.product.repository.ProductCategoryRepository;
import com.daejangjangi.backend.product.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final ProductCategoryRepository productCategoryRepository;
  private final FileValidator fileValidator;
  private final S3Manager s3Manager;

  /**
   * 상품 저장
   *
   * @param product      상품 정보
   * @param profileImage 프로필 이미지
   * @param categories   카테고리
   */
  public void register(Product product, MultipartFile profileImage, List<Category> categories) {
    fileValidator.validateImage(profileImage);
    String imageUrl = s3Manager.upload(profileImage);
    product.updateProfile(imageUrl);
    productRepository.save(product);
    List<ProductCategory> productCategories = saveCategories(product, categories);
    product.addCategories(productCategories);
  }

  /**
   * 추천 알고리즘 조회
   *
   * @param member 로그인 회원
   * @param count  추천 갯수
   * @return List RecommendedProduct
   */
  public List<RecommendedProduct> getRecommendedProducts(Member member, int count) {
    // NOTE : 추후 추천 알고리즘 로직 추가 구현 예정
    List<RecommendedProduct> recommendedProducts = new ArrayList<>();
    for (long i = 1; i <= count; i++) {
      Product product = productRepository.findById(i).orElseThrow();
      RecommendedProduct recommendedProduct = ProductMapper.INSTANCE.entityToResponse(product);
      recommendedProducts.add(recommendedProduct);
    }
    return recommendedProducts;
  }

  /**
   * 상품 카테고리 저장
   *
   * @param product    상품 정보
   * @param categories 카테고리
   * @return List - ProductCategory
   */
  private List<ProductCategory> saveCategories(Product product, List<Category> categories) {
    List<ProductCategory> productCategories = new ArrayList<>();
    for (Category category : categories) {
      ProductCategory productCategory = ProductCategory.builder()
          .product(product)
          .category(category)
          .build();
      productCategories.add(productCategory);
    }
    return productCategoryRepository.saveAll(productCategories);
  }
}
