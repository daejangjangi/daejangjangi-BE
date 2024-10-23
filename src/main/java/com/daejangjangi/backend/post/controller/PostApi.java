package com.daejangjangi.backend.post.controller;

import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.post.domain.dto.PostRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Post (게시글) API", description = "게시글 관련 API")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "OK",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ApiGlobalResponse.class),
            examples = @ExampleObject(
                value = """
                    {
                      "code": "OK",
                      "message": "OK",
                      "data": null
                    }"""
            )
        )
    ),
    @ApiResponse(responseCode = "500", description = "서버 내부 오류",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ApiGlobalResponse.class),
            examples = @ExampleObject(
                value = """
                    {
                      "code": "INTERNAL_SERVER_ERROR",
                      "message": "서버 내부 오류 입니다.",
                      "data": null
                    }"""
            )
        )
    )
})
public interface PostApi {

  @Operation(summary = "게시글 생성", tags = {"Post (게시글) API"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "401", description = "미인증",
          content = @Content(
              mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = ApiGlobalResponse.class)),
              examples = {
                  @ExampleObject(
                      name = "NOT_AUTHENTICATED_ACCESS",
                      summary = "인증되지 않은 접근",
                      value = """
                          {
                            "code": "UNAUTHENTICATED",
                            "message": "로그인 후 이용 바랍니다.",
                            "data": null
                          }
                          """
                  ),
                  @ExampleObject(
                      name = "EXPIRED_TOKEN",
                      summary = "만료된 토큰",
                      value = """
                          {
                            "code": "EXPIRED_TOKEN",
                            "message": "만료된 토큰입니다.",
                            "data": null
                          }
                          """
                  ),
                  @ExampleObject(
                      name = "INVALID_JWT_SIGNATURE",
                      summary = "유효하지 않은 서명",
                      value = """
                          {
                            "code": "INVALID_JWT_SIGNATURE",
                            "message": "유효하지 않은 서명입니다.",
                            "data": null
                          }
                          """
                  ),
                  @ExampleObject(
                      name = "UNAUTHENTICATED",
                      summary = "인증되지 않음",
                      value = """
                          {
                            "code": "UNAUTHENTICATED",
                            "message": "로그인 후 이용 바랍니다.",
                            "data": null
                          }
                          """
                  ),
                  @ExampleObject(
                      name = "INVALID_TOKEN_ERROR",
                      summary = "유효하지 않은 토큰",
                      value = """
                          {
                            "code": "INVALID_TOKEN_ERROR",
                            "message": "유효하지 않는 토큰입니다.",
                            "data": null
                          }
                          """
                  )
              }
          )
      ),
      @ApiResponse(responseCode = "400", description = "잘못된 요청",
          content = @Content(
              mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = ApiGlobalResponse.class)),
              examples = {
                  // Title
                  @ExampleObject(
                      name = "BAD_REQUEST_TITLE_BLANK",
                      summary = "제목 미입력",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "title : 제목을 입력하세요."
                            ]
                          }"""
                  ),
                  @ExampleObject(
                      name = "BAD_REQUEST_TITLE_SIZE",
                      summary = "제목 사이즈 오류",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "title : 제목은 최대 100자리 이하이어야 합니다."
                            ]
                          }"""
                  ),
                  // Content
                  @ExampleObject(
                      name = "BAD_REQUEST_CONTENT_BLANK",
                      summary = "내용 미입력",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "content : 내용을 입력해주세요."
                            ]
                          }"""
                  ),
                  @ExampleObject(
                      name = "BAD_REQUEST_CONTENT_SIZE",
                      summary = "내용 사이즈 오류",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "content : 내용은 최대 1000자리 이하이어야 합니다."
                            ]
                          }"""
                  ),
                  // Boards
                  @ExampleObject(
                      name = "BAD_REQUEST_BOARDS_BLANK",
                      summary = "게시판 미선택",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "boards : 게시판을 선택해주세요."
                            ]
                          }"""
                  ),
                  @ExampleObject(
                      name = "BAD_REQUEST_BOARDS_SIZE",
                      summary = "게시판 사이즈 오류",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "boards : 게시판을 최소 1개 이상, 최대 2개 이상 선택 바랍니다."
                            ]
                          }"""
                  ),
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
  ApiGlobalResponse<?> createPost(
      @RequestBody PostRequestDto.CreatePost request
  );


  @Operation(summary = "게시글 수정", tags = {"Post (게시글) API"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "401", description = "미인증",
          content = @Content(
              mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = ApiGlobalResponse.class)),
              examples = {
                  @ExampleObject(
                      name = "NOT_AUTHENTICATED_ACCESS",
                      summary = "인증되지 않은 접근",
                      value = """
                          {
                            "code": "UNAUTHENTICATED",
                            "message": "로그인 후 이용 바랍니다.",
                            "data": null
                          }
                          """
                  ),
                  @ExampleObject(
                      name = "EXPIRED_TOKEN",
                      summary = "만료된 토큰",
                      value = """
                          {
                            "code": "EXPIRED_TOKEN",
                            "message": "만료된 토큰입니다.",
                            "data": null
                          }
                          """
                  ),
                  @ExampleObject(
                      name = "INVALID_JWT_SIGNATURE",
                      summary = "유효하지 않은 서명",
                      value = """
                          {
                            "code": "INVALID_JWT_SIGNATURE",
                            "message": "유효하지 않은 서명입니다.",
                            "data": null
                          }
                          """
                  ),
                  @ExampleObject(
                      name = "UNAUTHENTICATED",
                      summary = "인증되지 않음",
                      value = """
                          {
                            "code": "UNAUTHENTICATED",
                            "message": "로그인 후 이용 바랍니다.",
                            "data": null
                          }
                          """
                  ),
                  @ExampleObject(
                      name = "INVALID_TOKEN_ERROR",
                      summary = "유효하지 않은 토큰",
                      value = """
                          {
                            "code": "INVALID_TOKEN_ERROR",
                            "message": "유효하지 않는 토큰입니다.",
                            "data": null
                          }
                          """
                  )
              }
          )
      ),
      @ApiResponse(responseCode = "400", description = "잘못된 요청",
          content = @Content(
              mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = ApiGlobalResponse.class)),
              examples = {
                  // Id
                  @ExampleObject(
                      name = "BAD_REQUEST_ID_NULL",
                      summary = "게시글 아이디 미입력",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "id : 게시글 아이디를 입력하세요."
                            ]
                          }"""
                  ),
                  @ExampleObject(
                      name = "NOT_FOUND_POST",
                      summary = "존재하지 않는 게시글",
                      value = """
                          {
                            "code": "NOT_FOUND_POST",
                            "message": "존재하지 않는 게시글입니다.",
                            "data": null
                          }
                          """
                  ),
                  // Title
                  @ExampleObject(
                      name = "BAD_REQUEST_TITLE_BLANK",
                      summary = "제목 미입력",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "title : 제목을 입력하세요."
                            ]
                          }"""
                  ),
                  @ExampleObject(
                      name = "BAD_REQUEST_TITLE_SIZE",
                      summary = "제목 사이즈 오류",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "title : 제목은 최대 100자리 이하이어야 합니다."
                            ]
                          }"""
                  ),
                  // Content
                  @ExampleObject(
                      name = "BAD_REQUEST_CONTENT_BLANK",
                      summary = "내용 미입력",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "content : 내용을 입력해주세요."
                            ]
                          }"""
                  ),
                  @ExampleObject(
                      name = "BAD_REQUEST_CONTENT_SIZE",
                      summary = "내용 사이즈 오류",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "content : 내용은 최대 1000자리 이하이어야 합니다."
                            ]
                          }"""
                  ),
                  // Boards
                  @ExampleObject(
                      name = "BAD_REQUEST_BOARDS_BLANK",
                      summary = "게시판 미선택",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "boards : 게시판을 선택해주세요."
                            ]
                          }"""
                  ),
                  @ExampleObject(
                      name = "BAD_REQUEST_BOARDS_SIZE",
                      summary = "게시판 사이즈 오류",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "boards : 게시판을 최소 1개 이상, 최대 2개 이상 선택 바랍니다."
                            ]
                          }"""
                  ),
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
                  ),
                  @ExampleObject(
                      name = "NOT_POST_AUTHOR",
                      summary = "수정 권한이 없는 사용자",
                      value = """
                          {
                            "code": "NOT_POST_AUTHOR",
                            "message": "게시글의 작성자가 아니므로 수정할 수 없습니다.",
                            "data": null
                          }
                          """
                  )
              }
          )
      )
  })
  ApiGlobalResponse<?> modifyPost(
      @RequestBody PostRequestDto.ModifyPost request
  );

  @Operation(summary = "게시글 좋아요", tags = {"Post (게시글) API"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "401", description = "미인증",
          content = @Content(
              mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = ApiGlobalResponse.class)),
              examples = {
                  @ExampleObject(
                      name = "NOT_AUTHENTICATED_ACCESS",
                      summary = "인증되지 않은 접근",
                      value = """
                          {
                            "code": "UNAUTHENTICATED",
                            "message": "로그인 후 이용 바랍니다.",
                            "data": null
                          }
                          """
                  ),
                  @ExampleObject(
                      name = "EXPIRED_TOKEN",
                      summary = "만료된 토큰",
                      value = """
                          {
                            "code": "EXPIRED_TOKEN",
                            "message": "만료된 토큰입니다.",
                            "data": null
                          }
                          """
                  ),
                  @ExampleObject(
                      name = "INVALID_JWT_SIGNATURE",
                      summary = "유효하지 않은 서명",
                      value = """
                          {
                            "code": "INVALID_JWT_SIGNATURE",
                            "message": "유효하지 않은 서명입니다.",
                            "data": null
                          }
                          """
                  ),
                  @ExampleObject(
                      name = "UNAUTHENTICATED",
                      summary = "인증되지 않음",
                      value = """
                          {
                            "code": "UNAUTHENTICATED",
                            "message": "로그인 후 이용 바랍니다.",
                            "data": null
                          }
                          """
                  ),
                  @ExampleObject(
                      name = "INVALID_TOKEN_ERROR",
                      summary = "유효하지 않은 토큰",
                      value = """
                          {
                            "code": "INVALID_TOKEN_ERROR",
                            "message": "유효하지 않는 토큰입니다.",
                            "data": null
                          }
                          """
                  )
              }
          )
      ),
      @ApiResponse(responseCode = "400", description = "잘못된 요청",
          content = @Content(
              mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = ApiGlobalResponse.class)),
              examples = {
                  @ExampleObject(
                      name = "NOT_FOUND_POST",
                      summary = "존재하지 않는 게시글",
                      value = """
                          {
                            "code": "NOT_FOUND_POST",
                            "message": "존재하지 않는 게시글입니다.",
                            "data": null
                          }
                          """
                  )
              }
          )
      )
  })
  ApiGlobalResponse<?> LikePost(@PathVariable("postId") Long postId);


  @Operation(summary = "게시글 상세 조회", tags = {"Post (게시글) API"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "401", description = "미인증",
          content = @Content(
              mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = ApiGlobalResponse.class)),
              examples = {
                  @ExampleObject(
                      name = "NOT_AUTHENTICATED_ACCESS",
                      summary = "인증되지 않은 접근",
                      value = """
                          {
                            "code": "UNAUTHENTICATED",
                            "message": "로그인 후 이용 바랍니다.",
                            "data": null
                          }
                          """
                  ),
                  @ExampleObject(
                      name = "EXPIRED_TOKEN",
                      summary = "만료된 토큰",
                      value = """
                          {
                            "code": "EXPIRED_TOKEN",
                            "message": "만료된 토큰입니다.",
                            "data": null
                          }
                          """
                  ),
                  @ExampleObject(
                      name = "INVALID_JWT_SIGNATURE",
                      summary = "유효하지 않은 서명",
                      value = """
                          {
                            "code": "INVALID_JWT_SIGNATURE",
                            "message": "유효하지 않은 서명입니다.",
                            "data": null
                          }
                          """
                  ),
                  @ExampleObject(
                      name = "UNAUTHENTICATED",
                      summary = "인증되지 않음",
                      value = """
                          {
                            "code": "UNAUTHENTICATED",
                            "message": "로그인 후 이용 바랍니다.",
                            "data": null
                          }
                          """
                  ),
                  @ExampleObject(
                      name = "INVALID_TOKEN_ERROR",
                      summary = "유효하지 않은 토큰",
                      value = """
                          {
                            "code": "INVALID_TOKEN_ERROR",
                            "message": "유효하지 않는 토큰입니다.",
                            "data": null
                          }
                          """
                  )
              }
          )
      ),
      @ApiResponse(responseCode = "400", description = "잘못된 요청",
          content = @Content(
              mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = ApiGlobalResponse.class)),
              examples = {
                  @ExampleObject(
                      name = "NOT_FOUND_POST",
                      summary = "존재하지 않는 게시글",
                      value = """
                          {
                            "code": "NOT_FOUND_POST",
                            "message": "존재하지 않는 게시글입니다.",
                            "data": null
                          }
                          """
                  )
              }
          )
      )
  })
  ApiGlobalResponse<?> info(@PathVariable("postId") Long postId);
}