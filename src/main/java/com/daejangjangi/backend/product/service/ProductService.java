package com.daejangjangi.backend.product.service;

import static com.daejangjangi.backend.product.domain.entity.QProduct.product;

import com.daejangjangi.backend.category.domain.Category;
import com.daejangjangi.backend.disease.domain.Disease;
import com.daejangjangi.backend.file.service.FileValidator;
import com.daejangjangi.backend.file.service.S3Manager;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.product.domain.doc.ProductLookUpLog;
import com.daejangjangi.backend.product.domain.dto.ProductLookUpLogDto;
import com.daejangjangi.backend.product.domain.dto.ProductResponseDto.ProductInfo;
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
import com.daejangjangi.backend.product.repository.ProductLogRepository;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
  private final ProductLogRepository productLogRepository;

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

  private static final String[] DEFAULT_PRODUCT_LIST = {
      "추석선물세트 푸룬 건자두 2구 선물세트 넛츠앤 37호",
      "프로바이오틱스 유산균19 30포(2개) MN970254 [원산지:국산]",
      "커클랜드 그릭 요거트 플레인 논팻 907g [원산지:미국]",
      "KAMUT 골드 카무트 효소 3g x 30포 1통 [원산지:상세설명에 표시]",
      "Brelat 브렐렛 모짜렐라 치즈 125g [원산지:상세설명에 표시]",
      "버터넛 마운틴 팜 메이플 시럽 237ml [원산지:미국]"
  };

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
   * 상품 조회 로그 추가
   *
   * @param product 상품 정보
   * @param member  회원 정보
   */
  public void createLookUpLog(Product product, Member member) {
    /**
     * 로그에 필요한 데이터
     * - 조회 시간
     * - 상품 ID
     * - 조회 회원 ID
     * - type : view(확장성 고려)
     */
    ProductLookUpLog lookUpLog = ProductLookUpLog.builder()
        .memberId(member.getId())
        .productId(product.getId())
        .build();
    productLogRepository.save(lookUpLog);
  }

  /**
   * 추천 알고리즘 조회
   *
   * @param member 로그인 회원
   * @param count  추천 갯수
   * @return List RecommendedProduct
   */
  public List<RecommendedProduct> getRecommendedProductsInMain(Member member, int count) {
    List<Product> recommendedProduct = getRecommendedProductList(member, count);
    return ProductMapper.INSTANCE.recommendProductsToDtoList(recommendedProduct);
  }

  public List<ProductInfo> getRecommendedProductsInDaejanggan(Member member, int count) {
    List<Product> recommendedProduct = getRecommendedProductList(member, count);
    return ProductMapper.INSTANCE.productToInfoDto(recommendedProduct, member);
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

    // 서브쿼리로 ID 조회
    List<Long> productIds = jpaQueryFactory
        .select(product.id)
        .distinct()
        .from(product)
        .leftJoin(product.productGroups)
        .where(predicate)
        .orderBy(orderSpecifier)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    // 카운트 쿼리
    Long total = jpaQueryFactory
        .select(product.countDistinct())
        .from(product)
        .leftJoin(product.productGroups)
        .where(predicate)
        .fetchOne();

    // 결과 조회
    List<Product> results = jpaQueryFactory
        .selectFrom(product)
        .leftJoin(product.discount).fetchJoin()
        .where(product.id.in(productIds))
        .orderBy(orderSpecifier)
        .fetch();

    return new PageImpl<>(results, pageable, total != null ? total : 0L);
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
   * 최신 인기 상품 조회
   *
   * @return List Product
   */
  public List<Product> getPopularProducts() {
    List<Product> productList;
    List<ProductLookUpLogDto> logs = getTopViewedProductList();
    // TODO : 로직 최적화 예정
    if (logs.size() >= 6) {
      List<Long> productIdList = logs.stream().map(ProductLookUpLogDto::getProductId).toList();
      productList = productRepository.findByIdIn(productIdList);
    } else {
      productList = productRepository.findByNameIn(Arrays.asList(DEFAULT_PRODUCT_LIST));

      if (productList.size() < 6) {
        throw new RuntimeException("기본 조회 상품이 정상적으로 등록되지 않았습니다. DB를 확인해주세요.");
      }
    }
    return productList;
  }

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
//          new OrderSpecifier<>(Order.DESC, product.createdAt),
          new OrderSpecifier<>(Order.DESC, product.id)
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

  /**
   * 추천 상품 목록 조회
   *
   * @param member 로그인 회원 정보
   * @param count  추천 상품 갯수
   * @return List Product
   */
  private List<Product> getRecommendedProductList(Member member, int count) {
    List<String> diseases = member.getDiseases().stream()
        .map(d -> d.getDisease().getName()).toList();
    List<String> categories = member.getCategories().stream()
        .map(c -> c.getCategory().getName()).toList();
    List<Product> recommendedProducts
        = productRepository.findMyProductList(diseases, categories, count);
    if (recommendedProducts.size() < count) {
      recommendedProducts = productRepository.findRandomProductList(count);
    }
    return recommendedProducts;
  }

  private List<ProductLookUpLogDto> getTopViewedProductList() {
    LocalDateTime toDate = LocalDateTime.now();
    LocalDateTime fromDate = toDate.minusDays(10);
    Date from = Date.from(fromDate.atZone(ZoneId.systemDefault()).toInstant());
    Date to = Date.from(toDate.atZone(ZoneId.systemDefault()).toInstant());
    return productLogRepository.findByLookedAtBetween(from, to);
  }
}
