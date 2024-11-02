package com.daejangjangi.backend.cardnews.domain.mapper;

import com.daejangjangi.backend.cardnews.domain.dto.CardnewsRequestDto.Register;
import com.daejangjangi.backend.cardnews.domain.dto.CardnewsResponseDto;
import com.daejangjangi.backend.cardnews.domain.dto.CardnewsResponseDto.Info;
import com.daejangjangi.backend.cardnews.domain.entity.Cardnews;
import com.daejangjangi.backend.cardnews.domain.entity.CardnewsImage;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CardnewsMapper {

  CardnewsMapper INSTANCE = Mappers.getMapper(CardnewsMapper.class);

  @Mapping(target = "title", source = "request.title")
  Cardnews registerRequestToEntity(Register request);

  @Mapping(target = "title", source = "cardnews.title")
  @Mapping(target = "createdAt", source = "cardnews.createdAt")
  @Mapping(target = "newsImages", expression = "java(asStringList(cardnews))")
  Info entityToInfoResponse(Cardnews cardnews);

  default List<String> asStringList(Cardnews cardnews) {
    List<CardnewsImage> images = cardnews.getNewsImages();
    return images.stream().map(CardnewsImage::getImage).toList();
  }

  @Mapping(target = "profile", source = "cardnews.profile")
  @Mapping(target = "title", source = "cardnews.title")
  List<CardnewsResponseDto.CardnewsItem> entityToDto(List<Cardnews> cardnews);
}
