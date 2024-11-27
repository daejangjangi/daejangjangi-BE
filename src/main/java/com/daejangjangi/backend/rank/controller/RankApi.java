package com.daejangjangi.backend.rank.controller;

import com.daejangjangi.backend.global.annotation.swagger.Response200WithSwagger;
import com.daejangjangi.backend.global.annotation.swagger.Response401WithSwagger;
import com.daejangjangi.backend.global.annotation.swagger.Response403WithSwagger;
import com.daejangjangi.backend.global.annotation.swagger.ResponseCommonWithSwagger;
import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.rank.domain.RankResponseDto.SearchRankingTop;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.ObjectUtils.Null;

@Tag(name = "Rank (랭킹) API", description = "랭킹 관련 API")
@ResponseCommonWithSwagger
public interface RankApi {

  @Operation(summary = "상위 랭킹 조회", tags = {"Rank (랭킹) API"},
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "OK",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = SearchRankingTop.class)
              )
          )
      })
  @Response401WithSwagger
  @Response403WithSwagger
  ApiGlobalResponse<SearchRankingTop> searchRankingTop();

  @Operation(summary = "하위 랭킹 검색 키워드 일괄 삭제", tags = {"Rank (랭킹) API"})
  @Response200WithSwagger
  @Response401WithSwagger
  @Response403WithSwagger
  ApiGlobalResponse<Null> deleteLowRanking();
}
