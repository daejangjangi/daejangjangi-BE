package com.daejangjangi.backend.product.service;

import com.daejangjangi.backend.category.domain.Category;
import com.daejangjangi.backend.disease.domain.Disease;
import com.daejangjangi.backend.file.service.FileValidator;
import com.daejangjangi.backend.file.service.S3Manager;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.product.domain.dto.ProductResponseDto.RecommendedProduct;
import com.daejangjangi.backend.product.domain.entity.Product;
import com.daejangjangi.backend.product.domain.entity.ProductCategory;
import com.daejangjangi.backend.product.domain.entity.ProductDisease;
import com.daejangjangi.backend.product.domain.mapper.ProductMapper;
import com.daejangjangi.backend.product.exception.NotFoundProductException;
import com.daejangjangi.backend.product.repository.ProductCategoryRepository;
import com.daejangjangi.backend.product.repository.ProductDiseaseRepository;
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
  private final ProductDiseaseRepository productDiseaseRepository;

  /**
   * 상품 저장
   *
   * @param product      상품 정보
   * @param profileImage 프로필 이미지
   * @param diseases     장질환
   * @param categories   카테고리
   */
  public void register(
      Product product,
      MultipartFile profileImage,
      List<Disease> diseases,
      List<Category> categories
  ) {
    fileValidator.validateImage(profileImage);
    String imageUrl = s3Manager.upload(profileImage);
    product.updateProfile(imageUrl);
    productRepository.save(product);
    if (!diseases.isEmpty()) {
      List<ProductDisease> productDiseases = saveDisease(product, diseases);
      product.addDiseases(productDiseases);
    }
    if (!categories.isEmpty()) {
      List<ProductCategory> productCategories = saveCategories(product, categories);
      product.addCategories(productCategories);
    }
  }

  /**
   * 추천 알고리즘 조회
   *
   * @param member 로그인 회원
   * @param count  추천 갯수
   * @return List RecommendedProduct
   */
  public List<RecommendedProduct> getRecommendedProducts(Member member, int count) {
    List<String> myDiseases = member.getDiseases().stream()
        .map(d -> d.getDisease().getName()).toList();
    List<String> myCategories = member.getCategories().stream()
        .map(c -> c.getCategory().getName()).toList();
    List<Product> recommendedProduct
        = productRepository.findMyProductList(myDiseases, myCategories, count);
    return ProductMapper.INSTANCE.recommendProductsToDtoList(recommendedProduct);
  }

  /**
   * 관련 장질환 저장
   *
   * @param product  상품 정보
   * @param diseases 장질환
   * @return List - ProductDisease
   */
  private List<ProductDisease> saveDisease(Product product, List<Disease> diseases) {
    List<ProductDisease> productDiseases = new ArrayList<>();
    for (Disease disease : diseases) {
      ProductDisease productDisease = ProductDisease.builder()
          .product(product)
          .disease(disease)
          .build();
      productDiseases.add(productDisease);
    }
    return productDiseaseRepository.saveAll(productDiseases);
  }

  /**
   * 회원 추천 카테고리 저장
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

  /**
   * 상품 조회
   *
   * @param productId 상품 ID
   * @return Product
   */
  public Product findById(Long productId) {
    return productRepository.findById(productId).orElseThrow(NotFoundProductException::new);
  }
}
