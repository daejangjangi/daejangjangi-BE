package com.daejangjangi.backend.reels.domain.mapper;

import com.daejangjangi.backend.reels.domain.dto.ReelsRequestDto;
import com.daejangjangi.backend.reels.domain.dto.ReelsResponseDto;
import com.daejangjangi.backend.reels.domain.dto.ReelsResponseDto.ReelsInfo;
import com.daejangjangi.backend.reels.domain.entity.Reels;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReelsMapper {

  ReelsMapper INSTANCE = Mappers.getMapper(ReelsMapper.class);

  @Mapping(target = "title", source = "request.title")
  Reels requestToEntity(ReelsRequestDto.Register request);

  @IterableMapping(elementTargetType = ReelsResponseDto.ReelsInfo.class, qualifiedByName = "reelsToReelsInfo")
  List<ReelsInfo> entityToResponse(List<Reels> reelsList);

  @Named("reelsToReelsInfo")
  @Mapping(target = "id", source = "reels.id")
  @Mapping(target = "title", source = "reels.title")
  @Mapping(target = "reelsVideo", source = "reels.reelsVideo")
  ReelsInfo reelsToReelsInfo(Reels reels);
}
