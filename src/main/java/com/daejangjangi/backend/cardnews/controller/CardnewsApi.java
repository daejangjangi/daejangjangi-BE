package com.daejangjangi.backend.cardnews.controller;

import com.daejangjangi.backend.cardnews.domain.dto.CardnewsRequestDto;
import com.daejangjangi.backend.cardnews.domain.dto.CardnewsResponseDto;
import com.daejangjangi.backend.global.annotation.swagger.Response200WithSwagger;
import com.daejangjangi.backend.global.annotation.swagger.Response401WithSwagger;
import com.daejangjangi.backend.global.annotation.swagger.Response403WithSwagger;
import com.daejangjangi.backend.global.annotation.swagger.ResponseCommonWithSwagger;
import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.apache.commons.lang3.ObjectUtils.Null;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Cardnews (카드뉴스) API", description = "카드뉴스 관련 API")
@ResponseCommonWithSwagger
public interface CardnewsApi {

  @Operation(summary = "카드뉴스 등록", tags = {"Cardnews (카드뉴스) API"},
      responses = {
          @ApiResponse(
              responseCode = "400",
              description = "잘못된 입력",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ApiGlobalResponse.class),
                  examples = {
                      @ExampleObject(
                          value = """
                              {
                                "code" : "BAD_REQUEST",
                                "message" : "잘못된 요청입니다.",
                                "data" : [
                                  "title : 제목을 입력해주세요.",
                                  "overview : 제목은 최대 20자 이하이어야 합니다."
                                ]
                              }
                              """
                      )
                  }
              )
          )
      })
  @Response200WithSwagger
  @Response401WithSwagger
  @Response403WithSwagger
  ApiGlobalResponse<Null> register(
      @Parameter CardnewsRequestDto.Register request,
      @Parameter MultipartFile profileImage,
      @Parameter List<MultipartFile> cardnewsImages
  );

  @Operation(summary = "카드뉴스 삭제", tags = {"Cardnews (카드뉴스) API"},
      responses = {
          @ApiResponse(
              responseCode = "400",
              description = "미존재 카드뉴스",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ApiGlobalResponse.class),
                  examples = {
                      @ExampleObject(
                          value = """
                              {
                                "code" : "NOT_FOUND_CARDNEWS",
                                "message" : "존재하지 않는 카드뉴스입니다.",
                                "data" : null
                              }
                              """
                      )
                  }
              )
          )
      }
  )
  @Response200WithSwagger
  @Response401WithSwagger
  @Response403WithSwagger
  ApiGlobalResponse<Null> remove(
      @Parameter Long cardnewsId
  );

  @Operation(summary = "카드뉴스 목록 조회", tags = {"Cardnews (카드뉴스) API"},
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "OK",
              content = @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = CardnewsResponseDto.Cardnews.class))
              )
          )
      }
  )
  @Response401WithSwagger
  @Response403WithSwagger
  ApiGlobalResponse<CardnewsResponseDto.Cardnews> cardnews();

  @Operation(summary = "카드뉴스 상세 조회", tags = {"Cardnews (카드뉴스) API"},
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "OK",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CardnewsResponseDto.Info.class)
              )
          ),
          @ApiResponse(
              responseCode = "400",
              description = "미존재 카드뉴스",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ApiGlobalResponse.class),
                  examples = {
                      @ExampleObject(
                          value = """
                              {
                                "code" : "NOT_FOUND_CARDNEWS",
                                "message" : "존재하지 않는 카드뉴스입니다.",
                                "data" : null
                              }
                              """
                      )
                  }
              )
          )
      }
  )
  @Response401WithSwagger
  @Response403WithSwagger
  ApiGlobalResponse<CardnewsResponseDto.Info> info(
      @Parameter Long cardnewsId
  );

  @Operation(summary = "최근 카드뉴스 조회", tags = {"Cardnews (카드뉴스) API"},
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "OK",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CardnewsResponseDto.Info.class)
              )
          )
      }
  )
  @Response401WithSwagger
  @Response403WithSwagger
  ApiGlobalResponse<CardnewsResponseDto.Info> recent();
}
