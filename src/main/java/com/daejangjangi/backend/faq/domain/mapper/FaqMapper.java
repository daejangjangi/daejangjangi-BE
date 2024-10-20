package com.daejangjangi.backend.faq.domain.mapper;

import com.daejangjangi.backend.faq.domain.dto.FaqRequestDto.Register;
import com.daejangjangi.backend.faq.domain.dto.FaqResponseDto.Faqs;
import com.daejangjangi.backend.faq.domain.entity.Faq;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FaqMapper {

  FaqMapper INSTANCE = Mappers.getMapper(FaqMapper.class);

  @Mapping(target = "category", source = "registerRequest.category")
  @Mapping(target = "question", source = "registerRequest.question")
  Faq registerRequestToEntity(Register registerRequest);

  @Mapping(target = "category", source = "faqs.category")
  @Mapping(target = "question", source = "faqs.question")
  @Mapping(target = "answer", source = "faqs.answer")
  List<Faqs> entityToFaqsResponse(List<Faq> faqs);
}
