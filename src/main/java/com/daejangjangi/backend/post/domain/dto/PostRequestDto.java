package com.daejangjangi.backend.post.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.*;

public class PostRequestDto {

  @Schema(description = "게시글 생성 DTO")
  public record CreatePost(

      @Schema(description = "제목", defaultValue = "게시글 제목")
      @Size(max = 100)
      @NotBlank
      String title,

      @Schema(description = "내용", defaultValue = "게시글 내용")
      @Size(max = 10000)
      @NotBlank
      String content,

      // TODO : 게시판 카테고리 최대 개수 제한 제약 사항 추가 필요
      @Schema(description = "게시판 주제 (최소 1개 이상)", allowableValues = {"자유", "변비", "과민성장증후군설사형",
          "과민성장증후군변비형", "치질", "치핵", "변실금",
          "항문소양증", "대장암", "크론병", "궤양성대장염", "복부팽만", "기타"})
      @Size(min = 1)
      List<String> boards
  ) {

  }

  @Schema(description = "게시글 수정 DTO")
  public record ModifyPost(

      @Schema(description = "게시글 id")
      Long id,

      @Schema(description = "제목")
      @Size(max = 100)
      @Size
      String title,

      @Schema(description = "내용")
      @Size(max = 10000)
      @NotBlank
      String content,

      @Schema(description = "게시판 카테고리", allowableValues = {"자유", "변비", "과민성장증후군설사형", "과민성장증후군변비형",
          "치질", "치핵", "변실금",
          "항문소양증", "대장암", "크론병", "궤양성대장염", "복부팽만", "기타"})
      @Size(min = 1)
      List<String> boards

  ) {

  }

}
