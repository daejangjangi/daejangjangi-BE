package com.daejangjangi.backend.post.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;

public class PostResponseDto {

  public record Info(
      @Schema(description = "제목")
      String title,

      @Schema(description = "내용")
      String content,

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
      int hit,

      @Schema(description = "좋아요 수")
      int likeCount,

      @Schema(description = "좋아요 여부")
      boolean isLiked
  ) {

  }

}
