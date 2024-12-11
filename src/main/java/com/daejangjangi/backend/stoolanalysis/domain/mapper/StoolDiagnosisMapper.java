package com.daejangjangi.backend.stoolanalysis.domain.mapper;

import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisRequestDto.Register;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisRequestDto.Stools;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisResponseDto.StoolDiagnosticAiResult;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisResponseDto.StoolDiagnosticResult;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisResponseDto.StoolImageAiAnalysis;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisResponseDto.StoolImageAnalysis;
import com.daejangjangi.backend.stoolanalysis.domain.entity.StoolDiagnosis;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StoolDiagnosisMapper {

  StoolDiagnosisMapper INSTANCE = Mappers.getMapper(StoolDiagnosisMapper.class);

  @Mapping(target = "stoolAt", expression = "java(convertToSeoulTime(stoolInfo.stoolAt()))")
  @Mapping(target = "color", source = "stoolInfo.color")
  @Mapping(target = "form", source = "stoolInfo.form")
  @Mapping(target = "isBloody", source = "stoolInfo.isBloody")
  @Mapping(target = "bloodyStoolDescription", source = "stoolInfo.bloodyStoolDescription")
  @Mapping(target = "proteinLumps", source = "stoolInfo.proteinLumps")
  @Mapping(target = "mucus", source = "stoolInfo.mucus")
  @Mapping(target = "additionalDescription", source = "registerRequest.stoolDiagnose.dietDescription")
  @Mapping(target = "dietType", source = "registerRequest.stoolDiagnose.dietType")
  @Mapping(target = "dietDescription", source = "registerRequest.stoolDiagnose.additionalDescription")
  @Mapping(target = "diagnosisDescription", source = "registerRequest.diagnosisDescription")
  @Mapping(target = "member", source = "member")
  StoolDiagnosis requestToEntity(Register registerRequest, Stools stoolInfo, Member member);

  @Mapping(target = "color", source = "stool.color")
  @Mapping(target = "form", source = "stool.form")
  @Mapping(target = "date", expression = "java(convertToSeoulTime(stool.stoolAt()))")
  @Mapping(target = "result", source = "result.userLanguage")
  StoolDiagnosticResult stoolDiagnosticAiResultToResponse(Stools stool,
      StoolDiagnosticAiResult result);

  @Mapping(target = "stoolImageAiAnalysis", source = "stoolImageAiAnalysis")
  @Mapping(target = "stoolImageUrl", source = "stoolImageUrl")
  StoolImageAnalysis stoolImageAnalysisToResponse(StoolImageAiAnalysis stoolImageAiAnalysis,
      String stoolImageUrl);

  default LocalDateTime convertToSeoulTime(LocalDateTime stoolAt) {
    ZonedDateTime utcZoned = stoolAt.atZone(ZoneId.of("UTC"));
    ZonedDateTime seoulZoned = utcZoned.withZoneSameInstant(ZoneId.of("Asia/Seoul"));
    return seoulZoned.toLocalDateTime();
  }

}
