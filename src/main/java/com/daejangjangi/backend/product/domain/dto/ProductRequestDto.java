package com.daejangjangi.backend.product.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public class ProductRequestDto {

  @Schema(name = "ProductRegisterRequest", description = "상품 등록 요청 DTO")
  public record Register(

      @Schema(description = "상품명", example = "추석선물세트 푸룬 건자두 2구 선물세트 넛츠앤 37호")
      @NotNull(message = "상품명을 입력해주세요.")
      String name,

      @Schema(description = "상품 코멘트", example = "변비를 극복하는 푸룬 하나 어떠세요")
      @NotNull(message = "상품 코멘트를 입력해주세요.")
      String comment,

      @Schema(description = "상품 판매 링크", example = "https://smartstore.naver.com/daejangganstore/products/10771915399")
      @NotNull(message = "상품 판매 링크를 입력해주세요.")
      String saleLink,

      @Schema(description = "상품 가격", type = "Long", example = "54080")
      @NotNull(message = "상품 가격을 입력해주세요.")
      Long regularPrice,

      @Schema(description = "상품 카테고리", allowableValues = {
          "유산균", "식이섬유", "저포드맵", "간식", "생활용품.리빙"
      })
      @Size(min = 1, message = "상품 카테고리를 최소 {min}개 이상 선택 바랍니다.")
      List<String> categories
  ) {

  }
}
