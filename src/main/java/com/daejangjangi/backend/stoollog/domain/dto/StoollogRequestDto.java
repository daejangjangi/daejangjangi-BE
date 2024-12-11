package com.daejangjangi.backend.stoollog.domain.dto;

import com.daejangjangi.backend.stoollog.domain.enums.Color;
import com.daejangjangi.backend.stoollog.domain.enums.Form;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class StoollogRequestDto {

  @Schema(description = "배변 일지 등록")
  public record StoollogRegisterRequest(

      @Schema(description = "배변 색상")
      @NotNull(message = "배변 색상을 선택해주세요.")
      Color color,

      @Schema(description = "배변 형태")
      @NotNull(message = "배변 형태를 선택해주세요.")
      Form form,

      @Schema(description = "배변 활동한 날짜와 시 (UTC)", examples = "2024-11-26T11:00:00.00Z")
      @NotNull(message = "배변 활동한 날짜와 시를 선택해주세요.")
      LocalDateTime loggedAt
  ) {

  }

  @Schema(description = "배변 일지 수정")
  public record StoollogModifyRequest(

      @Schema(description = "배변 일지 id")
      Long id,

      @Schema(description = "배변 색상")
      @NotNull(message = "배변 색상을 선택해주세요.")
      Color color,

      @Schema(description = "배변 형태")
      @NotNull(message = "배변 형태를 선택해주세요.")
      Form form,

      @Schema(description = "배변 활동한 날짜와 시 (UTC)", examples = "2024-11-26T11:00:00.00Z")
      @NotNull(message = "배변 활동한 날짜와 시를 선택해주세요.")
      LocalDateTime loggedAt
  ) {

  }

}
