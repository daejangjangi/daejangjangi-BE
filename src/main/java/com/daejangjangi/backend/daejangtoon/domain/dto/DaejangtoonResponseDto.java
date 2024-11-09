package com.daejangjangi.backend.daejangtoon.domain.dto;

import com.daejangjangi.backend.daejangtoon.domain.enums.Yoil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public class DaejangtoonResponseDto {

  @Schema(name = "DaejangtoonResponse", description = "대장툰 응답 DTO")
  public record Daejangtoon(
      @Schema(description = "대장툰 id", example = "1")
      Long id,

      @Schema(description = "대장툰 제목", example = "대장툰")
      String title,

      @Schema(description = "대장툰 개요", example = "어쩌구 저쩌구")
      String overview,

      @Schema(description = "대장툰 연재 요일", allowableValues = {"월", "화", "수", "목", "금", "토", "일"})
      Yoil yoil,

      @Schema(description = "대장툰 회차 목록")
      List<DaejangtoonChapters> chapters
  ) {

  }

  @Schema(name = "DaejangtoonChaptersResponse", description = "대장툰 목록 응답 DTO")
  public record DaejangtoonChapters(
      @Schema(description = "대장툰 회차 id", example = "1")
      Long id,

      @Schema(description = "대장툰 회차", type = "Integer", example = "4")
      Integer chapter,

      @Schema(description = "대장툰 회차 제목", example = "치핵이 뭔데? 2")
      String title,

      @Schema(description = "대장툰 회차 프로필 이미지", example = "이미지 링크")
      String profile,

      @Schema(description = "대장툰 회차 조회수", example = "350")
      Long hit,

      @Schema(description = "대장툰 회차 좋아요 갯수", example = "120")
      Integer likeCount,

      @Schema(description = "대장툰 로그인 회원 좋아요 여부", example = "true")
      Boolean isLiked
  ) {

  }

  @Schema(name = "DaejangtoonChapterResponse", description = "대장툰 응답 DTO")
  public record DaejangtoonChapter(

      @Schema(description = "대장툰 회차", type = "Integer", example = "4")
      Integer chapter,

      @Schema(description = "대장툰 회차 제목", example = "우울증 해결의 비밀은 장에 있다")
      String title,

      @Schema(description = "대장툰 회차 프로필", example = "https://xxxx.s3.xxxx.amazonaws.com/")
      String profile,

      @Schema(description = "대장툰 회차 이미지 목록", examples = {"https://xxxx.s3.xxxx.amazonaws.com/"})
      @JsonInclude(Include.NON_ABSENT)
      List<String> toonImages,

      @Schema(description = "대장툰 회차 좋아요 갯수", example = "120")
      Integer likeCount,

      @Schema(description = "대장툰 로그인 회원 좋아요 여부", example = "true")
      Boolean isLiked
  ) {

  }
}
