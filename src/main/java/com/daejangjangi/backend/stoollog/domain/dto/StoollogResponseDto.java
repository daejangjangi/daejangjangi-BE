package com.daejangjangi.backend.stoollog.domain.dto;

import com.daejangjangi.backend.stoollog.domain.enums.Color;
import com.daejangjangi.backend.stoollog.domain.enums.Form;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;

public class StoollogResponseDto {

  @Schema(description = "배변 일지 응답 DTO")
  public record StoollogInfoResponse(

      @Schema(description = "배변 일지 id")
      Long id,

      @Schema(description = "배변 색상")
      Color color,

      @Schema(description = "배변 형태")
      Form form,

      @Schema(description = "배변 활동한 날짜와 시")
      LocalDateTime loggedAt
  ) {

  }

  @Schema(description = "배변 일지 목록 응답 조회 DTO")
  public record StoollogInfoListResponse(

      @Schema(description = "배변 일지 목록")
      List<StoollogInfoResponse> stoollogInfoList
  ) {

  }


}
