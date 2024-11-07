package com.daejangjangi.backend.qna.domain.mapper;

import com.daejangjangi.backend.qna.domain.dto.QnaRequestDto.Register;
import com.daejangjangi.backend.qna.domain.dto.QnaResponseDto.MyInfo;
import com.daejangjangi.backend.qna.domain.entity.Qna;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface QnaMapper {

  QnaMapper INSTANCE = Mappers.getMapper(QnaMapper.class);

  @Mapping(target = "category", source = "request.category")
  @Mapping(target = "question", source = "request.question")
  Qna registerRequestToEntity(Register request);

  @Mapping(target = "id", source = "id")
  @Mapping(target = "category", source = "category")
  @Mapping(target = "status", source = "status")
  @Mapping(target = "question", source = "question")
  @Mapping(target = "answer", source = "answer")
  List<MyInfo> entityToMyQnaListResponse(List<Qna> myQnaList);
}
