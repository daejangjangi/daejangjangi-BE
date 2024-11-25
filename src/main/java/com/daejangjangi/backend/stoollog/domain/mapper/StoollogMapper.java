package com.daejangjangi.backend.stoollog.domain.mapper;

import com.daejangjangi.backend.stoollog.domain.dto.StoollogRequestDto;
import com.daejangjangi.backend.stoollog.domain.entity.Stoollog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StoollogMapper {

  StoollogMapper INSTANCE = Mappers.getMapper(StoollogMapper.class);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "color", source = "request.color")
  @Mapping(target = "form", source = "request.form")
  @Mapping(target = "loggedAt", source = "request.loggedAt")
  Stoollog registerRequestToEntity(StoollogRequestDto.StoollogRegisterRequest request);

  @Mapping(target = "id", source = "request.id")
  @Mapping(target = "color", source = "request.color")
  @Mapping(target = "form", source = "request.form")
  Stoollog modifyRequestToEntity(StoollogRequestDto.StoollogModifyRequest request);

}
