package com.daejangjangi.backend.cardnews.domain.mapper;

import com.daejangjangi.backend.cardnews.domain.entity.CardnewsImage;
import com.daejangjangi.backend.file.domain.ImageInfoDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CardnewsImageMapper {

  CardnewsImageMapper INSTANCE = Mappers.getMapper(CardnewsImageMapper.class);

  @Mapping(target = "order", source = "dtoList.order")
  @Mapping(target = "image", source = "dtoList.image")
  @Mapping(target = "key", source = "dtoList.key")
  List<CardnewsImage> dtoToEntity(List<ImageInfoDto> dtoList);
}