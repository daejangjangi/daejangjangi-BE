package com.daejangjangi.backend.product.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public class ProductResponseDto {

  @Schema(name = "RecommendedProductListResponse", description = "추천 상품 목록 응답 DTO")
  public record RecommendedProductList(
      List<RecommendedProduct> recommendedProducts
  ) {

  }

  @Schema(name = "RecommendedProductResponse", description = "추천 상품 응답 DTO")
  public record RecommendedProduct(

      @Schema(description = "상품 아이디", example = "1")
      Long id,

      @Schema(description = "상품명", example = "추석선물세트 푸룬 건자두 2구 선물세트 넛츠앤 37호")
      String name,

      @Schema(description = "상품 코멘트", example = "변비를 극복하는 푸룬 하나 어떠세요")
      String comment,

      @Schema(description = "상품 판매 링크", example = "https://smartstore.naver.com/daejangganstore/products/10771915399")
      String saleLink,

      @Schema(description = "상품 프로필 이미지", example = "이미지 링크")
      String profile
  ) {

  }
}
