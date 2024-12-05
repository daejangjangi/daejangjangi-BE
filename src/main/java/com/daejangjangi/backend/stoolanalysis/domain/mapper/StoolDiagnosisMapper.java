package com.daejangjangi.backend.stoolanalysis.domain.mapper;

import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisRequestDto.Register;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisRequestDto.Stools;
import com.daejangjangi.backend.stoolanalysis.domain.entity.StoolDiagnosis;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StoolDiagnosisMapper {

  StoolDiagnosisMapper INSTANCE = Mappers.getMapper(StoolDiagnosisMapper.class);

  @Mapping(target = "stoolAt", source = "stoolInfo.stoolAt")
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
}
