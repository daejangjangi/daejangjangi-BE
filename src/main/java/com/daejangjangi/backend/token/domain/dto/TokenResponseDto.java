package com.daejangjangi.backend.token.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(name = "ReissueResponse", description = "재발급 응답 DTO")
public class TokenResponseDto {

  @Schema(description = "액세스 토큰")
  private String accessToken;
  @Schema(description = "리프레쉬 토큰")
  private String refreshToken;
}
