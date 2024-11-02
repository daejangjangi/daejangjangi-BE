package com.daejangjangi.backend.cardnews.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CardnewsRequestDto {

  @Schema(name = "CardnewsRegisterRequest", description = "카드뉴스 등록 요청 DTO")
  public record Register(

      @Schema(description = "카드뉴스 제목", example = "장염과 식중독의 차이?")
      @NotBlank(message = "제목을 입력해주세요.")
      @Size(max = 20, message = "제목은 최대 {max}자 이하이어야 합니다.")
      String title
  ) {

  }
}