package com.daejangjangi.backend.post.domain.mapper;

import com.daejangjangi.backend.post.domain.dto.PostRequestDto;
import com.daejangjangi.backend.post.domain.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {})
public interface PostMapper {

  PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

  @Mapping(target = "title", source = "createRequest.title")
  @Mapping(target = "content", source = "createRequest.content")
  Post createRequestToEntity(PostRequestDto.CreatePost createRequest);

  @Mapping(target = "id", source = "modifyRequest.id")
  @Mapping(target = "title", source = "modifyRequest.title")
  @Mapping(target = "content", source = "modifyRequest.content")
  Post modifyRequestToEntity(PostRequestDto.ModifyPost modifyRequest);
}
