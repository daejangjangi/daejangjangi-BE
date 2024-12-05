package com.daejangjangi.backend.stoolanalysis.domain.dto;


import com.daejangjangi.backend.stoolanalysis.domain.enums.DietType;
import com.daejangjangi.backend.stoolanalysis.domain.enums.Mucus;
import com.daejangjangi.backend.stoolanalysis.domain.enums.ProteinLumps;
import com.daejangjangi.backend.stoollog.domain.enums.Color;
import com.daejangjangi.backend.stoollog.domain.enums.Form;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

public class StoolanalysisRequestDto {


  @Schema(name = "StoolImageAnalyzeRequest", description = "배변 이미지 분석 요청 DTO")
  public record ImageAnalyze(
      @Schema(description = "배변 이미지 분석에 필요한 이미지 aws url")
      @NotBlank(message = "배변 이미지 분석에 사용할 이미지 aws url을 입력하세요.")
      String imageUrl
  ) {

  }

  @Schema(name = "StoolDiagnoseRequest", description = "배변 진단 분석 요청 DTO")
  public record StoolDiagnose(
      List<Stools> stools,

      @Schema(description = "배변 상태 이외의 추가 증상")
      @Size(max = 500, message = "추가 증상은 최대 {max}자리 이하이어야 합니다.")
      String additionalDescription,

      @Schema(description = "오늘 먹은 음식 종류")
      DietType dietType,

      @Schema(description = "오늘 먹은 음식 설명")
      @Size(max = 500, message = "오늘 먹은 음식 설명은 최대 {max}자리 이하이어야 합니다.")
      String dietDescription
  ) {

  }

  @Schema(name = "StoolDetail", description = "배변 세부 정보")
  public record Stools(
      @Schema(description = "배변 분석일")
      LocalDateTime stoolAt,
      @Schema(description = "배변 색상")
      Color color,
      @Schema(description = "배변 묽기")
      Form form,
      @Schema(description = "혈변 여부")
      boolean isBloody,
      @Schema(description = "혈변 형태")
      String bloodyStoolDescription,
      @Schema(description = "배변에 뭉친 흰색 알갱이 존재 여부")
      ProteinLumps proteinLumps,
      @Schema(description = "배변 점액 여부")
      Mucus mucus
  ) {

  }

  @Schema(name = "DiagnosisResultRegisterRequest", description = "배변 분석 결과 저장 요청 DTO")
  public record Register(
      StoolDiagnose stoolDiagnose,
      @Schema(description = "배변 분석 결과")
      @NotBlank(message = "배변 분석 결과를 입력해주세요.")
      String diagnosisDescription,
      @Schema(description = "배변 이미지 분석에 사용된 이미지 aws url")
      @NotBlank(message = "배변 이미지 분석에 사용된 이미지 aws url을 입력하세요.")
      String stoolImageUrl
  ) {

  }
}
