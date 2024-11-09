package com.daejangjangi.backend.reels.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ReelsRequestDto {

  @Schema(name = "ReelsRegisterRequest", description = "릴스 등록 요청 DTO")
  public record Register(

      @Schema(description = "릴스 제목", example = "장이 편한 알배추구이 레시피")
      @NotBlank(message = "릴스 제목을 입력하세요.")
      @Size(max = 50, message = "릴스 제목은 최대 {max}이하이어야 합니다.")
      String title
  ) {

  }
}
