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

      @Schema(description = "관련 장질환", allowableValues = {
          "변비", "과민성장증후군_설사형", "과민성장증후군_변비형", "치질", "치핵", "치열", "변실금", "항문소양증",
          "대장암", "크론병", "궤양성대장염", "복부팽만", "없음"
      })
      @NotNull(message = "관련 장질환을 선택해주세요.")
      @Size(min = 1, message = "관련 장질환을 최소 {min}개 이상 선택 바랍니다.")
      List<String> diseases,

      @Schema(description = "회원 추천 카테고리", allowableValues = {
          "유산균", "식이섬유", "저포드맵", "비건", "욕실용품", "아동", "운동기구"
      })
      @NotNull(message = "회원 추천 카테고리를 선택해주세요.")
      @Size(min = 1, message = "회원 추천 카테고리를 최소 {min}개 이상 선택 바랍니다.")
      List<String> categories
  ) {

  }
}
