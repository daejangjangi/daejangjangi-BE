package com.daejangjangi.backend.stoollog.domain.mapper;

import com.daejangjangi.backend.stoollog.domain.dto.StoollogRequestDto;
import com.daejangjangi.backend.stoollog.domain.dto.StoollogResponseDto.StoollogInfoResponse;
import com.daejangjangi.backend.stoollog.domain.entity.Stoollog;
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
  @Mapping(target = "loggedAt", source = "request.loggedAt")
  Stoollog modifyRequestToEntity(StoollogRequestDto.StoollogModifyRequest request);

  @Named("stoollogToStoollogInfo")
  @Mapping(target = "id", source = "stoollog.id")
  @Mapping(target = "color", source = "stoollog.color")
  @Mapping(target = "form", source = "stoollog.form")
  @Mapping(target = "loggedAt", expression = "java(convertToSeoulTime(stoollog.getLoggedAt(), true))")
  StoollogInfoResponse stoollogToStoollogInfo(Stoollog stoollog);

  @IterableMapping(qualifiedByName = "stoollogToStoollogInfo")
  List<StoollogInfoResponse> entityToResponse(List<Stoollog> stoollogs);

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
