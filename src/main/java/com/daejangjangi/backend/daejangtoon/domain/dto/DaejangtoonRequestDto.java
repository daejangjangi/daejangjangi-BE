package com.daejangjangi.backend.daejangtoon.domain.dto;

import com.daejangjangi.backend.daejangtoon.domain.enums.Yoil;
import com.daejangjangi.backend.global.annotation.validation.ValidEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class DaejangtoonRequestDto {


  @Schema(name = "DaejangtoonRegisterRequest", description = "대장툰 등록 요청 DTO")
  public record Register(

      @Schema(description = "대장툰 제목", example = "대장툰")
      @NotBlank(message = "제목을 입력해주세요.")
      String title,

      @Schema(description = "대장툰 개요", example = "어쩌구 저쩌구")
      @NotBlank(message = "개요를 입력해주세요.")
      @Size(max = 250, message = "개요는 최대 {max}자 이하이어야 합니다.")
      String overview,

      @Schema(description = "대장툰 연재 요일", allowableValues = {"월", "화", "수", "목", "금", "토", "일"})
      @NotBlank(message = "연재 요일을 입력하세요")
      @ValidEnum(enumClass = Yoil.class, message = "지원하지 않는 연재 요일입니다.")
      String yoil
  ) {

  }

  @Schema(name = "DaejangtoonChapterRegisterRequest", description = "대장툰 회차 등록 요청 DTO")
  public record RegisterChapter(

      @Schema(description = "대장툰 회차", type = "Integer", example = "1")
      @NotNull(message = "회차를 입력해주세요.")
      @Min(value = 1, message = "회차는 최소 {value} 이상이어야 합니다.")
      Integer chapter,

      @Schema(description = "대장툰 제목", example = "치핵이 뭔데?")
      @NotBlank(message = "제목을 입력해주세요.")
      @Size(max = 20, message = "제목은 최대 {max} 이하이어야 합니다.")
      String title
  ) {

  }
}
