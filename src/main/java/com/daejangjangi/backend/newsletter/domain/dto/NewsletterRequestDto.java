package com.daejangjangi.backend.newsletter.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class NewsletterRequestDto {

  @Schema(name = "NewsletterRegisterRequest", description = "뉴스레터 등록 요청 DTO")
  public record Register(

      @Schema(description = "뉴스레터 제목", example = "디카페인 커피")
      @NotBlank(message = "제목을 입력하세요.")
      String title,

      @Schema(description = "뉴스레터 소제목", example = "슬기로운 장 건강 챙기는 디카페인 커피와 함께")
      @NotBlank(message = "소제목을 입력하세요.")
      String subTitle,

      @Schema(description = "뉴스레터 설명", example = "장문의 글,,,,")
      @NotBlank(message = "설명을 입력하세요.(줄개행 문자 포함)")
      String description,

      @Schema(description = "뉴스레터 카테고리", allowableValues = {
          "유산균", "식이섬유", "저포드맵", "간식", "생활용품.리빙"
      })
      @NotBlank(message = "뉴스레터 카테고리를 선택해주세요.")
      String category
  ) {

  }
}
