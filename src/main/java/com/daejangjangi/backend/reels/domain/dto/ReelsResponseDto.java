package com.daejangjangi.backend.reels.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public class ReelsResponseDto {

  @Schema(name = "ReelsInfoListResponse", description = "릴스 목록 조회 응답 DTO")
  public record ReelsInfoList(
      @Schema(description = "릴스 목록")
      List<ReelsInfo> reelsInfoList
  ) {

  }

  @Schema(name = "ReelsInfoResponse", description = "릴스 조회 응답 DTO")
  public record ReelsInfo(
      @Schema(description = "릴스 아이디", example = "1")
      Long id,
      @Schema(description = "릴스 제목", example = "장이 편한 알배추구이 레시피")
      String title,
      @Schema(description = "릴스 영상 링크", example = "영상 링크")
      String reelsVideo
  ) {

  }
}
