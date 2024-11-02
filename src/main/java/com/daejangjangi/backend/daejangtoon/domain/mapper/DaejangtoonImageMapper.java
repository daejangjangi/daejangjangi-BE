package com.daejangjangi.backend.daejangtoon.domain.mapper;

import com.daejangjangi.backend.daejangtoon.domain.entity.DaejangtoonImage;
import com.daejangjangi.backend.file.domain.ImageInfoDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DaejangtoonImageMapper {

  DaejangtoonImageMapper INSTANCE = Mappers.getMapper(DaejangtoonImageMapper.class);

  @Mapping(target = "order", source = "dtoList.order")
  @Mapping(target = "image", source = "dtoList.image")
  @Mapping(target = "key", source = "dtoList.key")
  List<DaejangtoonImage> dtoToEntity(List<ImageInfoDto> dtoList);
}
