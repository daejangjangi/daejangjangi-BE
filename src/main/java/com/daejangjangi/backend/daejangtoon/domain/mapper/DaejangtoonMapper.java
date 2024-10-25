package com.daejangjangi.backend.daejangtoon.domain.mapper;

import com.daejangjangi.backend.daejangtoon.domain.dto.DaejangtoonRequestDto;
import com.daejangjangi.backend.daejangtoon.domain.dto.DaejangtoonResponseDto;
import com.daejangjangi.backend.daejangtoon.domain.entity.Daejangtoon;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DaejangtoonMapper {

  DaejangtoonMapper INSTANCE = Mappers.getMapper(DaejangtoonMapper.class);

  @Mapping(target = "chapter", source = "registerRequest.chapter")
  @Mapping(target = "title", source = "registerRequest.title")
  @Mapping(target = "overview", source = "registerRequest.overview")
  @Mapping(target = "yoil", source = "registerRequest.yoil")
  Daejangtoon registerToEntity(DaejangtoonRequestDto.Register registerRequest);

  @Mapping(target = "chapter", source = "daejangtoons.chapter")
  @Mapping(target = "title", source = "daejangtoons.title")
  @Mapping(target = "overview", source = "daejangtoons.overview")
  @Mapping(target = "yoil", source = "daejangtoons.yoil")
  List<DaejangtoonResponseDto.Daejangtoons> entityToDaejangtoonsResponse(
      List<Daejangtoon> daejangtoons);
}
