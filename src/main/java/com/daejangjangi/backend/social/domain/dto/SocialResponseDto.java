package com.daejangjangi.backend.social.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class SocialResponseDto {

  @Schema(name = "SocialLoginResponse", description = "소셜 로그인 응답 DTO")
  public record SocialLogin(
      @Schema(description = "액세스 토큰", example = "xxxx.xxxx.xxxx")
      String accessToken,
      @Schema(description = "리프레쉬 토큰", example = "xxxx.xxxx.xxxx")
      String refreshToken
  ) {

  }
}
