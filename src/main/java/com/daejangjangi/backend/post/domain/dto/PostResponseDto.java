package com.daejangjangi.backend.post.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;

public class PostResponseDto {

  public record Info(

      @Schema(description = "게시글 아이디")
      Long id,

      @Schema(description = "제목")
      String title,

      @Schema(description = "내용")
      String content,

      @Schema(description = "작성자 닉네임")
      String nickname,

      @Schema(description = "생성일")
      LocalDateTime createdAt,

      @Schema(description = "조회 수")
      Long views,

      @Schema(description = "좋아요 수")
      Long likes,

      @Schema(description = "댓글 수")
      Long comments,

      @Schema(description = "인기글 여부")
      boolean isPopular
  ) {

  }


  public record DetailInfo(

      @Schema(description = "게시글 아이디")
      Long id,

      @Schema(description = "제목")
      String title,

      @Schema(description = "내용")
      String content,

      @Schema(description = "프로필 이미지")
      String profile,

      @Schema(description = "생성일")
      LocalDateTime createdAt,

      @Schema(description = "수정일")
      LocalDateTime updatedAt,

      @Schema(description = "작성자 닉네임")
      String nickname,

      @Schema(description = "카테고리")
      List<String> boards,

      @Schema(description = "작성자 여부")
      boolean isAuthor,

      @Schema(description = "조회 수")
      Long views,

      @Schema(description = "좋아요 수")
      Long likes,

      @Schema(description = "댓글 수")
      Long comments,

      @Schema(description = "좋아요 여부")
      boolean isLiked,

      @Schema(description = "인기글 여부")
      boolean isPopular,

      @Schema(description = "댓글")
      List<CommentInfo> commentInfo
  ) {

  }

  @Getter
  @Builder
  public static class CommentInfo {

    @Schema(description = "댓글 id")
    private Long id;

    @Schema(description = "내용")
    private String content;

    @Schema(description = "작성자 닉네임")
    private String nickname;

    @Schema(description = "프로필 이미지")
    private String profile;

    @Schema(description = "좋아요 수")
    private Long likes;

    @Schema(description = "생성일")
    private LocalDateTime createdAt;

    @Schema(description = "좋아요 여부")
    private boolean isLiked;

    @Schema(description = "작성자 여부")
    private boolean isAuthor;

    @Schema(description = "삭제 여부")
    private boolean isDeleted;

    @Schema(description = "대댓글")
    private List<CommentInfo> commentInfos;

    public void addCommentInfo(CommentInfo commentInfo) {
      if (Objects.isNull(commentInfos)) {
        commentInfos = new ArrayList<>();
      }
      this.commentInfos.add(commentInfo);
    }
  }

  public record Infos(
      @Schema(description = "게시글 정보들")
      List<Info> posts,

      @Schema(description = "현재 페이지 번호")
      int pageNumber,

      @Schema(description = "한 페이지에 표시되는 항목의 수")
      int pageSize,

      @Schema(description = "전체 데이터의 개수")
      long totalElements,

      @Schema(description = "전체 페이지 수")
      int totalPages
  ) {

  }

}
