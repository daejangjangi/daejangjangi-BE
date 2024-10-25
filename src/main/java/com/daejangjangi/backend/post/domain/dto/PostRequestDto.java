package com.daejangjangi.backend.post.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.*;

public class PostRequestDto {

  @Schema(description = "게시글 생성 DTO")
  public record CreatePost(

      @Schema(description = "제목", defaultValue = "게시글 제목")
      @Size(max = 100, message = "제목은 최대 {max}자리 이하이어야 합니다.")
      @NotBlank(message = "제목을 입력하세요.")
      String title,

      @Schema(description = "내용", defaultValue = "게시글 내용")
      @Size(max = 1000, message = "내용은 최대 {max}자리 이하이어야 합니다.")
      @NotBlank(message = "내용을 입력해주세요.")
      String content,

      @NotNull(message = "게시판을 선택해주세요.")
      @Schema(description = "게시판 주제",
          allowableValues = {"자유", "변비", "과민성장증후군_설사형", "과민성장증후군변비형", "치질", "치핵",
              "변실금", "항문소양증", "대장암", "크론병", "궤양성대장염", "복부팽만", "기타"})
      @Size(min = 1, max = 2, message = "게시판 주제를 최소 {min}개 이상, 최대 {max}개 이하 선택 바랍니다")
      List<String> boards
  ) {

  }

  @Schema(description = "게시글 수정 DTO")
  public record ModifyPost(

      @NotNull(message = "게시글 아이디를 입력하세요.")
      @Schema(description = "게시글 id")
      Long id,

      @Schema(description = "제목")
      @Size(max = 100, message = "제목은 최대 {max}자리 이하이어야 합니다.")
      @NotBlank(message = "제목을 입력하세요.")
      String title,

      @Schema(description = "내용")
      @Size(max = 1000, message = "내용은 최대 {max}자리 이하이어야 합니다.")
      @NotBlank(message = "내용을 입력해주세요.")
      String content,

      @NotNull(message = "게시판을 선택해주세요.")
      @Schema(description = "게시판 주제",
          allowableValues = {"자유", "변비", "과민성장증후군_설사형", "과민성장증후군변비형", "치질", "치핵",
              "변실금", "항문소양증", "대장암", "크론병", "궤양성대장염", "복부팽만", "기타"})
      @Size(min = 1, max = 2, message = "게시판 주제를 최소 {min}개 이상, 최대 {max}개 이하 선택 바랍니다")
      List<String> boards

  ) {

  }

}
