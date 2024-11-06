package com.daejangjangi.backend.faq.domain.mapper;

import com.daejangjangi.backend.faq.domain.dto.FaqResponseDto.FaqDto;
import com.daejangjangi.backend.faq.domain.entity.Faq;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FaqMapper {

  FaqMapper INSTANCE = Mappers.getMapper(FaqMapper.class);

  List<FaqDto> entityToFaqsResponse(List<Faq> faqs);

  @Mapping(target = "id", source = "faq.id")
  @Mapping(target = "category", expression = "java(faq.getQna().getCategory().name())")
  @Mapping(target = "question", expression = "java(faq.getQna().getQuestion())")
  @Mapping(target = "answer", expression = "java(faq.getQna().getAnswer())")
  FaqDto entityToFaqDto(Faq faq);
}
