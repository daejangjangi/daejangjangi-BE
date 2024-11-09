package com.daejangjangi.backend.newsletter.domain.mapper;

import com.daejangjangi.backend.newsletter.domain.dto.NewsletterRequestDto;
import com.daejangjangi.backend.newsletter.domain.dto.NewsletterResponseDto.NewsletterInfo;
import com.daejangjangi.backend.newsletter.domain.entity.Newsletter;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NewsletterMapper {

  NewsletterMapper INSTANCE = Mappers.getMapper(NewsletterMapper.class);

  @Mapping(target = "title", source = "request.title")
  @Mapping(target = "subTitle", source = "request.subTitle")
  @Mapping(target = "description", source = "request.description")
  Newsletter requestToEntity(NewsletterRequestDto.Register request);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "title", source = "newsletter.title")
  @Mapping(target = "subTitle", source = "newsletter.subTitle")
  @Mapping(target = "description", source = "newsletter.description")
  @Mapping(target = "profileImage", source = "newsletter.profileImage")
  @Mapping(target = "category", expression = "java(newsletter.getCategory().getName())")
  NewsletterInfo entityToResponse(Newsletter newsletter);

  @IterableMapping(elementTargetType = NewsletterInfo.class, qualifiedByName = "newsletterToInfo")
  List<NewsletterInfo> newslettersToInfoList(List<Newsletter> newsletters);

  @Named("newsletterToInfo")
  @Mapping(target = "id", source = "newsletter.id")
  @Mapping(target = "title", source = "newsletter.title")
  @Mapping(target = "subTitle", source = "newsletter.subTitle")
  @Mapping(target = "description", ignore = true)
  @Mapping(target = "profileImage", ignore = true)
  @Mapping(target = "category", expression = "java(newsletter.getCategory().getName())")
  NewsletterInfo newsletterToInfo(Newsletter newsletter);

}
