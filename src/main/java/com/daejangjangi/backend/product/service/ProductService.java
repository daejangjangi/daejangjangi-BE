package com.daejangjangi.backend.product.service;

import static com.daejangjangi.backend.product.domain.entity.QProduct.product;

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
import com.daejangjangi.backend.product.domain.entity.ProductGroup;
import com.daejangjangi.backend.product.domain.enums.ProductGroupEnum;
import com.daejangjangi.backend.product.domain.enums.ProductSortKey;
import com.daejangjangi.backend.product.domain.mapper.ProductMapper;
import com.daejangjangi.backend.product.exception.InvalidProductGroupException;
import com.daejangjangi.backend.product.exception.NotFoundProductException;
import com.daejangjangi.backend.product.repository.DiscountRepository;
import com.daejangjangi.backend.product.repository.ProductCategoryRepository;
import com.daejangjangi.backend.product.repository.ProductDiseaseRepository;
import com.daejangjangi.backend.product.repository.ProductGroupRepository;
import com.daejangjangi.backend.product.repository.ProductRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.PostConstruct;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@SuppressWarnings("NonAsciiCharacters")
@Slf4j
public class ProductService {

  private final ProductRepository productRepository;
  private final ProductCategoryRepository productCategoryRepository;
  private final FileValidator fileValidator;
  private final S3Manager s3Manager;
  private final ProductDiseaseRepository productDiseaseRepository;
  private final DiscountRepository discountRepository;
  private final JPAQueryFactory jpaQueryFactory;
  private final ProductGroupRepository productGroupRepository;
  private final RedisTemplate<String, String> redisTemplate;

  @Value("${custom.redis.bind.ranking-key}")
  private String searchRankingPrefix;

  @Value("${custom.redis.bind.user-keyword}")
  private String searchUserKeywordPrefix;

  private ZSetOperations<String, String> zSetOperations;
  private ValueOperations<String, String> valueOperations;

  @PostConstruct
  private void init() {
    zSetOperations = redisTemplate.opsForZSet();
    valueOperations = redisTemplate.opsForValue();
  }

  /**
   * 상품 저장
   *
   * @param product      상품 정보
   * @param profileImage 프로필 이미지
   * @param diseases     장질환
   * @param categories   카테고리
   * @param nameList     상품그룹명
   */
  public void register(
      Product product,
      MultipartFile profileImage,
      List<Disease> diseases,
      List<Category> categories,
      List<String> nameList
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
    if (!nameList.isEmpty()) {
      List<ProductGroup> productGroups = saveProductGroups(product, nameList);
      product.addProductGroups(productGroups);
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
   * @param name     상품 그룹명
   * @param pageable 페이지 정보
   */
  public Page<Product> getSearchedAndSortedProductList(
      String keyword,
      ProductSortKey sortKey,
      String name,
      Pageable pageable,
      Member member
  ) {

    BooleanBuilder predicate = new BooleanBuilder();

    // 키워드 검색 조건
    if (StringUtils.hasText(keyword)) {
      increaseScore(keyword, member.getId());
      predicate.and(
          product.name.like("%" + keyword + "%")
              .or(product.comment
                  .like("%" + keyword + "%")
              )
      );
    }

    // 상품 그룹 조건
    if (StringUtils.hasText(name)) {
      predicate.and(product.productGroups.any().name.eq(name));
    }

    // 정렬 조건
    OrderSpecifier<?>[] orderSpecifier = createOrderSpecifier(sortKey);

//  1. 우선 ID만 페이징하여 조회
    List<Long> productIds = jpaQueryFactory
        .select(product.id)
        .from(product)
        .leftJoin(product.productGroups)
        .where(predicate)
        .orderBy(orderSpecifier)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

//  2. 조회된 ID로 실제 데이터 조회
    JPAQuery<Product> query = jpaQueryFactory
        .selectFrom(product)
        .leftJoin(product.discount).fetchJoin()
        .where(product.id.in(productIds))
        .orderBy(orderSpecifier);

//  3. 카운트 쿼리
    JPAQuery<Long> countQuery = jpaQueryFactory
        .select(product.countDistinct())
        .from(product)
        .where(predicate);

    return PageableExecutionUtils.getPage(
        query.fetch(),
        pageable,
        countQuery::fetchOne
    );
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
   * 상품 그룹 검증
   *
   * @param productGroup 상품 그룹
   */
  public void validateProductGroup(String productGroup) {
    if (productGroup.trim().isEmpty()
        || Arrays.stream(ProductGroupEnum.values())
        .anyMatch(g -> g.getValue().equals(productGroup))) {
      return;
    }
    throw new InvalidProductGroupException();
  }

  /**
   * 상품 그룹 검증
   *
   * @param values 상품 그룹 목록
   */
  public void validateProductGroup(List<String> values) {
    for (String value : values) {
      validateProductGroup(value);
    }
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

  /*--------------Private----------------------------Private----------------------------Private---*/

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
   * 상품 그룹 저장
   *
   * @param product  상품 정보
   * @param nameList 상품 그룹 목록
   * @return List - ProductGroup
   */
  private List<ProductGroup> saveProductGroups(Product product, List<String> nameList) {
    List<ProductGroup> productGroupList = new ArrayList<>();
    for (String name : nameList) {
      ProductGroup productGroup = ProductGroup.builder()
          .product(product)
          .name(name)
          .build();
      productGroupList.add(productGroup);
    }
    return productGroupRepository.saveAll(productGroupList);
  }

  /**
   * 상품 정렬
   *
   * @param sortKey 상품 정렬 키
   * @return Page Product
   */
  private OrderSpecifier<?>[] createOrderSpecifier(ProductSortKey sortKey) {
    return switch (sortKey) {
      case 인기순 -> new OrderSpecifier[]{
          new OrderSpecifier<>(Order.DESC, product.productLikes.size()),
          new OrderSpecifier<>(Order.ASC, product.id)
      };
      case 낮은가격순 -> {
        NumberExpression<Integer> discountRate = new CaseBuilder()
            .when(product.discount.isNull())
            .then(0)
            .otherwise(product.discount.rate);
        NumberExpression<Integer> price = product.regularPrice.subtract(
            product.regularPrice.multiply(discountRate).divide(100)
        ).castToNum(Integer.class);
        yield new OrderSpecifier[]{
            new OrderSpecifier<>(Order.ASC, price),
            new OrderSpecifier<>(Order.ASC, product.id)
        };
      }
      default -> new OrderSpecifier[]{
          new OrderSpecifier<>(Order.DESC, product.createdAt),
          new OrderSpecifier<>(Order.ASC, product.id)
      };
    };
  }

  /**
   * 검색어 갯수 증가
   *
   * @param keyword  검색 키워드
   * @param memberId 회원 id - 동일 회원 중복 검색 키워드 입력 방지
   */
  private void increaseScore(String keyword, Long memberId) {
    String key = searchUserKeywordPrefix + memberId + ":" + keyword;
    if (keyword.equals(valueOperations.get(key))) {
      return;
    }
    valueOperations.set(key, keyword, Duration.ofMinutes(3));
    zSetOperations.incrementScore(searchRankingPrefix, keyword, 1);
  }
}
