package com.daejangjangi.backend.stoolanalysis.domain.mapper;

import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisRequestDto.StoolDiagnoseAi;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisRequestDto.Stools;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisResponseDto.StoolDiagnosticInfo;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisResponseDto.StoolDiagnosticResult;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisResponseDto.StoolImageAiAnalysis;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisResponseDto.StoolImageAnalysis;
import com.daejangjangi.backend.stoolanalysis.domain.entity.StoolDiagnosis;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StoolDiagnosisMapper {

  StoolDiagnosisMapper INSTANCE = Mappers.getMapper(StoolDiagnosisMapper.class);

  @Mapping(target = "stoolAt", expression = "java(convertToSeoulTime(stoolInfo.stoolAt(), true))")
  @Mapping(target = "color", source = "stoolInfo.color")
  @Mapping(target = "form", source = "stoolInfo.form")
  @Mapping(target = "isBloody", source = "stoolInfo.isBloody")
  @Mapping(target = "bloodyStoolDescription", source = "stoolInfo.bloodyStoolDescription")
  @Mapping(target = "proteinLumps", source = "stoolInfo.proteinLumps")
  @Mapping(target = "mucus", source = "stoolInfo.mucus")
  @Mapping(target = "additionalDescription", source = "aiRequest.dietDescription")
  @Mapping(target = "dietType", source = "aiRequest.dietType")
  @Mapping(target = "dietDescription", source = "aiRequest.additionalDescription")
  @Mapping(target = "diagnosisDescription", source = "diagnosisDescription")
  @Mapping(target = "member", source = "member")
  StoolDiagnosis requestToEntity(StoolDiagnoseAi aiRequest, Stools stoolInfo, Member member,
      String diagnosisDescription);

  @Mapping(target = "color", source = "stool.color")
  @Mapping(target = "form", source = "stool.form")
  @Mapping(target = "date", source = "stool.stoolAt")
  @Mapping(target = "diagnosticResult", source = "diagnosticResult")
  StoolDiagnosticResult stoolDiagnosticAiResultToResponse(Stools stool,
      String diagnosticResult);

  @Mapping(target = "stoolImageAiAnalysis", source = "stoolImageAiAnalysis")
  @Mapping(target = "stoolImageUrl", source = "stoolImageUrl")
  StoolImageAnalysis stoolImageAnalysisToResponse(StoolImageAiAnalysis stoolImageAiAnalysis,
      String stoolImageUrl);

  @Named("stoolDiagnosisToStoolDiagnosticInfo")
  @Mapping(target = "stoolDiagnosisId", source = "stoolDiagnosis.id")
  @Mapping(target = "color", source = "stoolDiagnosis.color")
  @Mapping(target = "form", source = "stoolDiagnosis.form")
  @Mapping(target = "stoolAt", source = "stoolDiagnosis.stoolAt")
  StoolDiagnosticInfo stoolDiagnosisToStoolDiagnosticInfo(StoolDiagnosis stoolDiagnosis);

  @IterableMapping(qualifiedByName = "stoolDiagnosisToStoolDiagnosticInfo")
  List<StoolDiagnosticInfo> entityToResponse(List<StoolDiagnosis> stoolDiagnoses);

  default LocalDateTime convertToSeoulTime(LocalDateTime stoolAt, boolean check) {
    if (check) {
      ZonedDateTime utcZoned = stoolAt.atZone(ZoneId.of("UTC"));
      ZonedDateTime seoulZoned = utcZoned.withZoneSameInstant(ZoneId.of("Asia/Seoul"));
      return seoulZoned.toLocalDateTime();
    } else {
      return stoolAt;
    }
  }
}
