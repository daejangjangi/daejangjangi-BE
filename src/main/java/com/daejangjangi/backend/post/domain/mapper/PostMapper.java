package com.daejangjangi.backend.post.domain.mapper;

import com.daejangjangi.backend.post.domain.dto.PostRequestDto;
import com.daejangjangi.backend.post.domain.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {})
public interface PostMapper {

  PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

  default Post createRequestToEntity(PostRequestDto.CreatePost createRequest) {
    return new Post(createRequest.title(), createRequest.content());
  }

  default Post modifyRequestToEntity(PostRequestDto.ModifyPost modifyRequest) {
    return new Post(modifyRequest.id(), modifyRequest.title(), modifyRequest.content());
  }
}
