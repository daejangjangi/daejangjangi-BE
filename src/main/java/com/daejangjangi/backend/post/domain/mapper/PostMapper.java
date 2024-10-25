package com.daejangjangi.backend.post.domain.mapper;

import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.post.domain.dto.PostRequestDto;
import com.daejangjangi.backend.post.domain.dto.PostResponseDto;
import com.daejangjangi.backend.post.domain.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {BoardPostMapper.class})
public interface PostMapper {

  PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

  default Post createRequestToEntity(PostRequestDto.CreatePost createRequest) {
    return new Post(createRequest.title(), createRequest.content());
  }

  default Post modifyRequestToEntity(PostRequestDto.ModifyPost modifyRequest) {
    return new Post(modifyRequest.id(), modifyRequest.title(), modifyRequest.content());
  }

  @Mapping(target = "nickname", source = "post.member.nickname")
  @Mapping(target = "title", source = "post.title")
  @Mapping(target = "content", source = "post.content")
  @Mapping(target = "createdAt", source = "post.createdAt")
  @Mapping(target = "updatedAt", source = "post.updatedAt")
  @Mapping(target = "hit", source = "post.hit")
  @Mapping(target = "likeCount", source = "post.likeCount")
  @Mapping(target = "boards", source = "post.boards")
  @Mapping(target = "isAuthor", expression = "java(isAuthor(post, member))")
  @Mapping(target = "isLiked", expression = "java(isLikedByMember(post, member))")
  PostResponseDto.Info entityToResponseDto(Post post, Member member);

  default boolean isLikedByMember(Post post, Member member) {
    return post.getLikes().stream().anyMatch(like -> like.getMember().equals(member));
  }

  default boolean isAuthor(Post post, Member member) {
    return post.getMember().equals(member);
  }
}
