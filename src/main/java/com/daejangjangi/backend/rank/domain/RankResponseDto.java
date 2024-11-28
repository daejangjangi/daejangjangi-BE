package com.daejangjangi.backend.rank.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;

public class RankResponseDto {

  @Schema(name = "SearchRankingTopResponseDto", description = "인기 검색어 목록 응답 DTO")
  public record SearchRankingTop(

      @Schema(description = "인기 검색어 목록", example = "[\"변비\",\"과민성\",\"식이섬유\"]")
      Set<String> top3
  ) {

  }
}
