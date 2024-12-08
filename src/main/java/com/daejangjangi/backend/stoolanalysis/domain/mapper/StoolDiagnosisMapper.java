package com.daejangjangi.backend.stoolanalysis.domain.mapper;

import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisRequestDto.Register;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisRequestDto.Stools;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisResponseDto.StoolDiagnosticAiResult;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisResponseDto.StoolDiagnosticResult;
import com.daejangjangi.backend.stoolanalysis.domain.entity.StoolDiagnosis;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StoolDiagnosisMapper {

  StoolDiagnosisMapper INSTANCE = Mappers.getMapper(StoolDiagnosisMapper.class);

  @Mapping(target = "stoolAt", expression = "java(stoolInfo.convertToSeoulTime())")
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

  @Mapping(target = "color", source = "stools.color")
  @Mapping(target = "form", source = "stools.form")
  @Mapping(target = "date", expression = "java(stools.convertToSeoulTime())")
  @Mapping(target = "result", source = "result.user_language")
  StoolDiagnosticResult stoolDiagnosticAiResultToResponse(Stools stools,
      StoolDiagnosticAiResult result);
}
