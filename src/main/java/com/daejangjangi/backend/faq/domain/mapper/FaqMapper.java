package com.daejangjangi.backend.faq.domain.mapper;

import com.daejangjangi.backend.faq.domain.dto.FaqRequestDto.Register;
import com.daejangjangi.backend.faq.domain.entity.Faq;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FaqMapper {

  FaqMapper INSTANCE = Mappers.getMapper(FaqMapper.class);

  @Mapping(target = "category", source = "registerRequest.category")
  @Mapping(target = "question", source = "registerRequest.question")
  Faq registerRequestToEntity(Register registerRequest);
}
