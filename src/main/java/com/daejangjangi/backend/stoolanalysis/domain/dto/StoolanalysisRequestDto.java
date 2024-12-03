package com.daejangjangi.backend.stoolanalysis.domain.dto;


import com.daejangjangi.backend.stoolanalysis.domain.enums.DietType;
import com.daejangjangi.backend.stoolanalysis.domain.enums.Mucus;
import com.daejangjangi.backend.stoolanalysis.domain.enums.ProteinLumps;
import com.daejangjangi.backend.stoollog.domain.enums.Color;
import com.daejangjangi.backend.stoollog.domain.enums.Form;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;

public class StoolanalysisRequestDto {


  @Schema(name = "StoolImageAnalyzeRequest", description = "배변 이미지 분석 요청 DTO")
  public record ImageAnalyze(
      String imageUrl
  ) {

  }

  @Schema(name = "StoolDiagnoseRequest", description = "배변 진단 분석 요청 DTO")
  public record StoolDiagnose(
      List<Stools> stools,
      String additionalDescription,
      DietType dietType,
      String dietDescription
  ) {

  }

  @Schema(name = "StoolDetail", description = "배변 세부 정보")
  public record Stools(
      LocalDateTime stoolAt,
      Color color,
      Form form,
      boolean isBloody,
      String bloodyStoolDescription,
      ProteinLumps proteinLumps,
      Mucus mucus
  ) {

  }
}
