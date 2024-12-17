package com.daejangjangi.backend.post.domain.mapper;

import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.post.domain.dto.PostRequestDto;
import com.daejangjangi.backend.post.domain.dto.PostResponseDto;
import com.daejangjangi.backend.post.domain.entity.Post;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(uses = {BoardPostMapper.class, CommentMapper.class})
public interface PostMapper {

  PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

  default Post createRequestToEntity(PostRequestDto.CreatePost createRequest) {
    return new Post(createRequest.title(), createRequest.content());
  }

  default Post modifyRequestToEntity(PostRequestDto.ModifyPost modifyRequest) {
    return new Post(modifyRequest.id(), modifyRequest.title(), modifyRequest.content());
  }

  @Mapping(target = "id", source = "post.id")
  @Mapping(target = "nickname", source = "post.member.nickname")
  @Mapping(target = "title", source = "post.title")
  @Mapping(target = "content", source = "post.content")
  @Mapping(target = "createdAt", source = "post.createdAt")
  @Mapping(target = "updatedAt", source = "post.updatedAt")
  @Mapping(target = "views", source = "post.hit")
  @Mapping(target = "likes", source = "post.likeCount")
  @Mapping(target = "boards", source = "post.boards")
  @Mapping(target = "isAuthor", expression = "java(isAuthor(post, member))")
  @Mapping(target = "isLiked", expression = "java(isLikedByMember(post, member))")
  @Mapping(target = "comments", source = "post.comments")
  @Mapping(target = "isPopular", expression = "java(post.getLikeCount() >= 5)")
  @Mapping(target = "commentInfo", source = "commentInfos")
  @Mapping(target = "profile", source = "member.profile")
  PostResponseDto.DetailInfo entityToPostDetailInfoResponse(Post post, Member member,
      List<PostResponseDto.CommentInfo> commentInfos);

  @Mapping(target = "id", source = "post.id")
  @Mapping(target = "title", source = "post.title")
  @Mapping(target = "content", source = "post.content")
  @Mapping(target = "nickname", source = "post.member.nickname")
  @Mapping(target = "createdAt", source = "post.createdAt")
  @Mapping(target = "views", source = "post.hit")
  @Mapping(target = "likes", source = "post.likeCount")
  @Mapping(target = "comments", source = "post.comments")
  @Mapping(target = "isPopular", expression = "java(post.getLikeCount() >= 5)")
  PostResponseDto.Info entityToPostInfoResponse(Post post);

  @Mapping(target = "posts", source = "posts.content")
  @Mapping(target = "pageNumber", expression = "java(getPageNumber(posts))")
  @Mapping(target = "pageSize", source = "posts.size")
  @Mapping(target = "totalElements", source = "posts.totalElements")
  @Mapping(target = "totalPages", source = "posts.totalPages")
  PostResponseDto.Infos entityToPostInfosResponse(Page<PostResponseDto.Info> posts);

  default boolean isLikedByMember(Post post, Member member) {
    return post.getLikes().stream().anyMatch(like -> like.getMember().equals(member));
  }

  default boolean isAuthor(Post post, Member member) {
    return post.getMember() != null && post.getMember().equals(member);
  }

  default int getPageNumber(Page<PostResponseDto.Info> posts) {
    if (posts.getTotalElements() > 0) {
      return posts.getNumber() + 1;
    } else {
      return 0;
    }
  }

  default LocalDateTime convertToSeoulTime(LocalDateTime date) {
    if (date != null) {
      ZonedDateTime utcZoned = date.atZone(ZoneId.of("UTC"));
      ZonedDateTime seoulZoned = utcZoned.withZoneSameInstant(ZoneId.of("Asia/Seoul"));
      return seoulZoned.toLocalDateTime();
    }
    return null;
  }
}
