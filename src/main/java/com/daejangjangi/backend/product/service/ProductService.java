package com.daejangjangi.backend.product.service;

import com.daejangjangi.backend.category.domain.Category;
import com.daejangjangi.backend.disease.domain.Disease;
import com.daejangjangi.backend.file.service.FileValidator;
import com.daejangjangi.backend.file.service.S3Manager;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.product.domain.dto.ProductResponseDto.RecommendedProduct;
import com.daejangjangi.backend.product.domain.entity.Discount;
import com.daejangjangi.backend.product.domain.entity.Product;
import com.daejangjangi.backend.product.domain.entity.ProductCategory;
import com.daejangjangi.backend.product.domain.entity.ProductDisease;
import com.daejangjangi.backend.product.domain.enums.ProductSortKey;
import com.daejangjangi.backend.product.domain.mapper.ProductMapper;
import com.daejangjangi.backend.product.exception.NotFoundProductException;
import com.daejangjangi.backend.product.repository.DiscountRepository;
import com.daejangjangi.backend.product.repository.ProductCategoryRepository;
import com.daejangjangi.backend.product.repository.ProductDiseaseRepository;
import com.daejangjangi.backend.product.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@SuppressWarnings("NonAsciiCharacters")
public class ProductService {

  private final ProductRepository productRepository;
  private final ProductCategoryRepository productCategoryRepository;
  private final FileValidator fileValidator;
  private final S3Manager s3Manager;
  private final ProductDiseaseRepository productDiseaseRepository;
  private final DiscountRepository discountRepository;

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
   * <p>
   * // TODO : 태그가 1개 이상 일치하는 상품들이 count 미만인 경우 랜덤한 상품 추가 조회하도록 추가 구현.
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

  /**
   * 검색된 상품 조회
   *
   * @param keyword  검색 키워드
   * @param sortKey  정렬 종류
   * @param pageable 페이지 정보
   */
  public Page<Product> getSearchedAndSortedProductList(
      String keyword,
      ProductSortKey sortKey,
      Pageable pageable
  ) {
    Page<Product> searchedProductList = productRepository.findByKeyword(keyword, pageable);
    return sortProductList(searchedProductList, sortKey);
  }

  /**
   * 할인 상품 등록
   *
   * @param product  상품
   * @param discount 할인
   */
  @Transactional
  public void registerAndUpdateDiscount(Product product, Discount discount) {
    Optional<Discount> discountOptional = discountRepository.findByName(discount.getName());
    if (discountOptional.isPresent()) {
      discount = discountOptional.get();
    } else {
      discount = discountRepository.save(discount);
    }
    product.discount(discount);
  }

  /**
   * TODO : 고민이 필요하다.
   * 조회 시마다 해당 상품에 대한 조회수를 rdb + redis에 카운팅한다.(이는
   *
   * @return List Product
   */
//  public List<Product> getBestProducts() {
//
//    return new ArrayList<>();
//  }

  /**
   * 상품 정렬
   *
   * @param searchedProductList 조회된 상품 목록
   * @param sortKey             상품 정렬 키
   * @return Page Product
   */
  private Page<Product> sortProductList(
      Page<Product> searchedProductList,
      ProductSortKey sortKey
  ) {
    List<Product> sortedContent = switch (sortKey) {
      case 인기순 -> searchedProductList.getContent().stream()
          .sorted((p1, p2) -> p2.getProductLikes().size() - p1.getProductLikes().size())
          .toList();
      case 낮은가격순 -> searchedProductList.getContent().stream()
          .sorted((p1, p2) -> {
            int p1Rate = p1.getDiscount() == null ? 0 : p1.getDiscount().getRate();
            int p2Rate = p2.getDiscount() == null ? 0 : p2.getDiscount().getRate();
            int p1Price = p1.getRegularPrice() - (int) (p1.getRegularPrice() * p1Rate * 0.01);
            int p2Price = p2.getRegularPrice() - (int) (p2.getRegularPrice() * p2Rate * 0.01);
            return p1Price - p2Price;
          })
          .toList();
      default -> searchedProductList.getContent();
    };

    return new PageImpl<>(
        sortedContent,
        searchedProductList.getPageable(),
        searchedProductList.getTotalElements()
    );
  }
}
