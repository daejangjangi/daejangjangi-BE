package com.daejangjangi.backend.fcm.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class FcmRequestDto {

  @Schema(description = "FCM 토큰 DTO")
  public record FcmtokenRequest(

      @Schema(description = "FCM 토큰", example = "bk3RNwTe3H0:CI2k_HHwgIpoDKCIZvvDMExUdFQ3P1...")
      @NotBlank(message = "FCM 토큰를 입력해주세요.")
      String fcmToken
  ) {

  }

}
