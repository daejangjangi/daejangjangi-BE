package com.daejangjangi.backend.product.domain.dto;

import com.daejangjangi.backend.global.common.PageFields;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
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

  @Schema(name = "ProductInfoListResponse", description = "상품 목록 응답 DTO")
  public record ProductInfoList(

      @Schema(description = "페이징 정보")
      PageFields pageFields,

      @Schema(description = "상품 목록")
      List<ProductInfo> myProductInfoList
  ) {

    public ProductInfoList {
      myProductInfoList = myProductInfoList == null ? new ArrayList<>() : myProductInfoList;
    }
  }

  @Schema(name = "BestProductListResponse", description = "인기 상품 목록 응답 DTO")
  public record BestProductList(
      @Schema(description = "인기 상품 목록")
      List<ProductInfo> bestProductList
  ) {

  }

  @Schema(name = "MyProductLikeResponse", description = "내가 좋아하는 상품 응답 DTO")
  public record ProductInfo(

      @Schema(description = "상품 아이디", example = "1")
      Long id,

      @Schema(description = "상품명", example = "차전차피 식이섬유")
      String name,

      @Schema(description = "상품 정가", example = "15000")
      Integer regularPrice,

      @Schema(description = "상품 할인율", example = "3")
      Integer discountRate,

      @Schema(description = "상품 판매 링크", example = "상품 판매 링크")
      String saleLink,

      @Schema(description = "상품 프로필 이미지", example = "상품 프로필 이미지")
      String profile,

      @Schema(description = "상품 로그인 회원 좋아요 여부", example = "true")
      Boolean isLiked,

      @Schema(description = "상품 태그", example = "[ \" 유산균 \", \" 식이섬유 \", \" 변비 \" ")
      List<String> tagList
  ) {

    public ProductInfo {
      tagList = tagList == null ? new ArrayList<>() : tagList;
    }
  }
}
