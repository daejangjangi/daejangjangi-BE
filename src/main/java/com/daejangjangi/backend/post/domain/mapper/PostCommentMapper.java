package com.daejangjangi.backend.post.domain.mapper;

import com.daejangjangi.backend.comment.domain.entity.PostComment;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.post.domain.dto.PostRequestDto;
import com.daejangjangi.backend.post.domain.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostCommentMapper {

  PostCommentMapper INSTANCE = Mappers.getMapper(PostCommentMapper.class);

  @Mapping(target = "content", source = "modifyRequest.content")
  PostComment modifyRequestToEntity(PostRequestDto.ModifyPostComment modifyRequest);

  @Mapping(target = "content", source = "createRequest.content")
  @Mapping(target = "post", source = "post")
  @Mapping(target = "member", source = "member")
  PostComment createRequestToEntity(PostRequestDto.CreatePostComment createRequest, Post post,
      Member member);


}
