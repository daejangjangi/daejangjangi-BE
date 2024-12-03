package com.daejangjangi.backend.stoolanalysis.domain.dto;

import com.daejangjangi.backend.stoolanalysis.domain.enums.Mucus;
import com.daejangjangi.backend.stoolanalysis.domain.enums.ProteinLumps;
import com.daejangjangi.backend.stoollog.domain.enums.Color;
import com.daejangjangi.backend.stoollog.domain.enums.Form;
import io.swagger.v3.oas.annotations.media.Schema;

public class StoolanalysisResponseDto {

  @Schema(name = "StoolImageInfoResponse", description = "배변 이미지 분석 응답 DTO")
  public record ImageInfo(

      @Schema(description = "배변 색상")
      Color color,

      @Schema(description = "배변 묽기")
      Form form,

      @Schema(description = "혈변 여부")
      boolean isBloody,

      @Schema(description = "배변 점액")
      Mucus mucus,

      // TODO 설명 수정
      @Schema(description = "배변 흰색 알갱이")
      ProteinLumps proteinLumps
  ) {

  }


  @Schema(name = "StoolInfoResponse", description = " DTO")
  public record StoolInfo(
      String user_language
  ) {

  }

}
