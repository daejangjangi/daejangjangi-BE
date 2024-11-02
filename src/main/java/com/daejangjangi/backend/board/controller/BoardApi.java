package com.daejangjangi.backend.board.controller;

import com.daejangjangi.backend.global.annotation.swagger.Response401WithSwagger;
import com.daejangjangi.backend.global.annotation.swagger.ResponseCommonWithSwagger;
import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.post.domain.dto.PostResponseDto.Info;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Board (게시판) API", description = "게시판 관련 API")
@ResponseCommonWithSwagger
public interface BoardApi {

  @Operation(summary = "게시판별로 게시글 조회", tags = {"Board (게시판) API"},
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "OK",
              content = @Content(
                  mediaType = "application/json",
                  examples = @ExampleObject(
                      value = """
                          {
                            "code": "OK",
                            "message": "OK",
                            "data": {
                              "content": [
                                {
                                  "id": 1,
                                  "title": "게시글 제목",
                                  "content": "게시글 내용",
                                  "createdAt": "2024-11-01T00:54:09.399766",
                                  "updatedAt": null,
                                  "nickname": "nick",
                                  "boards": [
                                    "자유"
                                  ],
                                  "isAuthor": true,
                                  "hit": 0,
                                  "likeCount": 0,
                                  "commentCount": 0,
                                  "isLiked": false
                                }
                              ],
                              "pageable": {
                                "pageNumber": 0,
                                "pageSize": 10,
                                "sort": {
                                  "empty": false,
                                  "unsorted": false,
                                  "sorted": true
                                },
                                "offset": 0,
                                "unpaged": false,
                                "paged": true
                              },
                              "last": true,
                              "totalElements": 1,
                              "totalPages": 1,
                              "first": true,
                              "size": 10,
                              "number": 0,
                              "sort": {
                                "empty": false,
                                "unsorted": false,
                                "sorted": true
                              },
                              "numberOfElements": 1,
                              "empty": false
                            }
                          }
                          """
                  )
              )
          )
      })
  @Response401WithSwagger
  @ApiResponses(value = {
      @ApiResponse(responseCode = "400", description = "잘못된 요청",
          content = @Content(
              mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = ApiGlobalResponse.class)),
              examples = {
                  // Board
                  @ExampleObject(
                      name = "NOT_MANAGED_BOARDS",
                      summary = "관리되지 않는 게시판 카테고리",
                      value = """
                          {
                            "code": "NOT_MANAGED_BOARD",
                            "message": "관리되지 않는 게시판 카테고리입니다.",
                            "data": null
                          }
                          """
                  )
              }
          )
      )
  })
  ApiGlobalResponse<Page<Info>> getPostsByBoard(
      @PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable,
      @RequestParam String board);

}
