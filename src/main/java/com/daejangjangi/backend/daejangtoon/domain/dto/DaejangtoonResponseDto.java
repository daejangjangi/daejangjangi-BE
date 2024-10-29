package com.daejangjangi.backend.daejangtoon.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class DaejangtoonResponseDto {

  @Schema(name = "DaejangtoonsResponse", description = "대장툰 목록 응답 DTO")
  public record Daejangtoons(
      @Schema(description = "대장툰 프로필 이미지", example = "이미지 링크")
      String profile,

      @Schema(description = "대장툰 제목", example = "치핵이 뭔데? 1")
      String title,

      @Schema(description = "대장툰 회차", type = "Integer", example = "4")
      Integer chapter,

      @Schema(description = "대장툰 조회수", example = "350")
      Long hit,

      @Schema(description = "대장툰 좋아요 갯수", example = "120")
      Integer likeCount
  ) {

  }
}
