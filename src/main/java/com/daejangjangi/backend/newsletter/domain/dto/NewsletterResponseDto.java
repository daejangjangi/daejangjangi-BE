package com.daejangjangi.backend.newsletter.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public class NewsletterResponseDto {

  @Schema(name = "NewsletterInfoListResponse", description = "뉴스레터 정보 목록 조회 DTO")
  public record NewsletterInfoList(
      List<NewsletterInfo> newsletterInfoList
  ) {

  }

  @Schema(name = "NewsletterInfoResponse", description = "뉴스레터 정보 조회 DTO")
  public record NewsletterInfo(
      @Schema(description = "뉴스레터 아이디", example = "1")
      @JsonInclude(JsonInclude.Include.NON_NULL)
      Long id,

      @Schema(description = "뉴스레터 제목", example = "디카페인 커피")
      String title,

      @Schema(description = "뉴스레터 소제목", example = "슬기로운 장 건강 챙기는 디카페인 커피와 함께")
      String subTitle,

      @Schema(description = "뉴스레터 설명", example = "장문의 설명 ...")
      @JsonInclude(JsonInclude.Include.NON_NULL)
      String description,

      @Schema(description = "뉴스레터 프로필", example = "이미지 링크")
      @JsonInclude(JsonInclude.Include.NON_NULL)
      String profileImage,

      @Schema(description = "뉴스레터 카테고리", allowableValues = {
          "유산균", "식이섬유", "저포드맵", "비건", "욕실용품", "아동", "운동기구"
      })
      String category
  ) {

  }
}
