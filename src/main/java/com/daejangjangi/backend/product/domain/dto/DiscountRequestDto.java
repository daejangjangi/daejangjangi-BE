package com.daejangjangi.backend.product.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DiscountRequestDto {

  @Schema(name = "discountRegisterRequest", description = "할인 등록 요청 DTO")
  public record DiscountRegister(
      @Schema(description = "할인명", example = "특가 10% 세일")
      @NotBlank(message = "할인명을 입력하세요.")
      String name,
      @Schema(description = "할인율", example = "10")
      @NotNull(message = "할인율을 입력하세요.")
      @Max(value = 100, message = "할인울은 최대 {value}이하 입니다.")
      @Min(value = 1, message = "할인율은 최소 {value}이상 입니다.")
      Integer rate
  ) {

  }
}
