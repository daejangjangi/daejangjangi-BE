package com.daejangjangi.backend.cardnews.domain.mapper;

import com.daejangjangi.backend.cardnews.domain.dto.CardnewsRequestDto.Register;
import com.daejangjangi.backend.cardnews.domain.entity.Cardnews;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CardnewsMapper {

  CardnewsMapper INSTANCE = Mappers.getMapper(CardnewsMapper.class);

  @Mapping(target = "title", source = "request.title")
  Cardnews registerRequestToEntity(Register request);


}