package com.daejangjangi.backend.member.controller;

import com.daejangjangi.backend.global.annotation.Response401WithSwagger;
import com.daejangjangi.backend.global.annotation.Response403WithSwagger;
import com.daejangjangi.backend.global.annotation.ResponseCommonWithSwagger;
import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.member.domain.dto.MemberRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Tag(name = "Member (회원) API", description = "회원 관련 API")
@ResponseCommonWithSwagger
public interface MemberApi {

  @Operation(summary = "이메일 중복 확인", tags = {"Member (회원) API"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "400", description = "이메일 중복",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiGlobalResponse.class),
              examples = @ExampleObject(
                  value = """
                      {
                        "code": "EMAIL_DUPLICATION_ERROR",
                        "message": "중복되는 메일 주소입니다.",
                        "data": null
                      }"""
              )
          )
      )
  })
  ApiGlobalResponse<?> emailDuplicationCheck(
      @Parameter String email
  );

  @Operation(summary = "닉네임 중복 확인", tags = {"Member (회원) API"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "400", description = "닉네임 중복",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiGlobalResponse.class),
              examples = @ExampleObject(
                  value = """
                      {
                        "code": "NICKNAME_DUPLICATION_ERROR",
                        "message": "중복되는 닉네임입니다.",
                        "data": null
                      }"""
              )
          )
      )
  })
  ApiGlobalResponse<?> nicknameDuplicationCheck(
      @Parameter String nickname
  );

  @Operation(summary = "회원가입", tags = {"Member (회원) API"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "400", description = "잘못된 요청",
          content = @Content(
              mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = ApiGlobalResponse.class)),
              examples = {
                  // Email
                  @ExampleObject(
                      name = "BAD_REQUEST_EMAIL_BLANK",
                      summary = "이메일 미입력",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "email : 이메일을 입력하세요."
                            ]
                          }"""
                  ),
                  @ExampleObject(
                      name = "BAD_REQUEST_EMAIL_FORMAT",
                      summary = "이메일 형식 오류",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "email : 이메일 형식이 맞지 않습니다."
                            ]
                          }"""
                  ),
                  @ExampleObject(
                      name = "BAD_REQUEST_EMAIL_SIZE",
                      summary = "이메일 사이즈 오류",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "email : 이메일은 최대 50자리 이하이어야 합니다."
                            ]
                          }"""
                  ),
                  // Password
                  @ExampleObject(
                      name = "BAD_REQUEST_PW_BLANK",
                      summary = "비밀번호 미입력",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "password : 비밀번호를 입력해주세요."
                            ]
                          }"""
                  ),
                  @ExampleObject(
                      name = "BAD_REQUEST_PW_SIZE",
                      summary = "비밀번호 사이즈 오류",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "password : 비밀번호는 최소 8자리 이상, 최대 16자리 이하이어야 합니다."
                            ]
                          }"""
                  ),
                  @ExampleObject(
                      name = "BAD_REQUEST_PW_EXCLUDE_NUMBER",
                      summary = "비밀번호 숫자 미포함",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "password : 비밀번호에 숫자가 포함되어야 합니다."
                            ]
                          }"""
                  ),
                  @ExampleObject(
                      name = "BAD_REQUEST_PW_EXCLUDE_LETTER",
                      summary = "비밀번호 영문자 미포함",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "password : 비밀번호에 영문자가 포함되어야 합니다."
                            ]
                          }"""
                  ),
                  @ExampleObject(
                      name = "BAD_REQUEST_PW_EXCLUDE_SPECIAL",
                      summary = "비밀번호 특수문자(!,@,#,,$,%,^,&,*,(,),[,]) 미포함",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "password : 비밀번호에 특수문자(!,@,#,$,%,^,&,*,(,),[,])가 포함되어야 합니다."
                            ]
                          }"""
                  ),
                  // Nickname
                  @ExampleObject(
                      name = "BAD_REQUEST_NICKNAME_BLANK",
                      summary = "닉네임 미입력",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "nickname : 닉네임을 입력하세요."
                            ]
                          }"""
                  ),
                  @ExampleObject(
                      name = "BAD_REQUEST_NICKNAME_SIZE",
                      summary = "닉네임 사이즈 오류",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "nickname : 닉네임은 최대 5자 이하이어야 합니다."
                            ]
                          }"""
                  ),
                  @ExampleObject(
                      name = "BAD_REQUEST_NICKNAME_FORMAT",
                      summary = "닉네임 형식(영문자 + 한글) 오류",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "nickname : 닉네임은 영문자,한글,숫자로만 입력해야 합니다."
                            ]
                          }"""
                  ),
                  // Gender
                  @ExampleObject(
                      name = "BAD_REQUEST_GENDER_BLANK",
                      summary = "성별 미입력",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "gender : 성별을 입력하세요."
                            ]
                          }"""
                  ),
                  @ExampleObject(
                      name = "BAD_REQUEST_GENDER_SIZE",
                      summary = "성별 사이즈 오류",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "gender : 성별은 최대 1자 이하이어야 합니다."
                            ]
                          }"""
                  ),
                  @ExampleObject(
                      name = "BAD_REQUEST_GENDER_FORMAT",
                      summary = "성별 형식(m or w) 오류",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "gender : 성별은 m(남성) 또는 w(여성)를 입력해야 합니다."
                            ]
                          }"""
                  ),
                  // Birth
                  @ExampleObject(
                      name = "BAD_REQUEST_BIRTH_BLANK",
                      summary = "생일 미입력",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "birth : 생일을 입력하세요."
                            ]
                          }"""
                  ),
                  // Diseases
                  @ExampleObject(
                      name = "BAD_REQUEST_DISEASES_BLANK",
                      summary = "질병 미선택",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "diseases : 질병을 선택해주세요."
                            ]
                          }"""
                  ),
                  @ExampleObject(
                      name = "BAD_REQUEST_DISEASES_SIZE",
                      summary = "질병 사이즈 오류",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "diseases : 질병을 최소 1개 이상 선택 바랍니다."
                            ]
                          }"""
                  ),
                  // Categories
                  @ExampleObject(
                      name = "BAD_REQUEST_CATEGORIES_BLANK",
                      summary = "관심 상품 미선택",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "categories : 관심 상품을 선택해주세요."
                            ]
                          }"""
                  ),
                  @ExampleObject(
                      name = "BAD_REQUEST_CATEGORIES_SIZE",
                      summary = "관심 상품 사이즈 오류",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "categories : 관심 상품을 최소 1개 이상 선택 바랍니다."
                            ]
                          }"""
                  )
              }
          )
      )
  })
  ApiGlobalResponse<?> join(
      @RequestBody MemberRequestDto.Join request
  );

  @Operation(summary = "로그인", tags = {"Member (회원) API"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "400", description = "잘못된 요청",
          content = @Content(
              mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = ApiGlobalResponse.class)),
              examples = {
                  @ExampleObject(
                      name = "BAD_REQUEST_EMAIL",
                      summary = "이메일 미입력",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "email : 이메일을 입력해주세요."
                            ]
                          }"""
                  ),
                  @ExampleObject(
                      name = "BAD_REQUEST_PASSWORD",
                      summary = "비밀번호 미입력",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "password : 비밀번호를 입력해주세요."
                            ]
                          }"""
                  ),
              }
          )
      )
  })
  void login(
      @RequestBody MemberRequestDto.Login loginRequest,
      HttpServletRequest request,
      HttpServletResponse response
  ) throws ServletException, IOException;

  @Operation(summary = "회원 정보 조회", tags = {"Member (회원) API"})
  @Response401WithSwagger
  @Response403WithSwagger
  ApiGlobalResponse<?> info();

  @Operation(summary = "회원 정보 수정", tags = {"Member (회원) API"})
  @Response401WithSwagger
  @Response403WithSwagger
  @ApiResponses(value = {
      @ApiResponse(responseCode = "400", description = "잘못된 요청",
          content = @Content(
              mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = ApiGlobalResponse.class)),
              examples = {
                  @ExampleObject(
                      name = "NOT_MANAGED_DISEASE",
                      summary = "관리되지 않는 질병",
                      value = """
                          {
                            "code": "NOT_MANAGED_DISEASE",
                            "message": "관리되지 않는 질병입니다.",
                            "data": null
                          }
                          """
                  ),
                  @ExampleObject(
                      name = "NOT_MANAGED_CATEGORY",
                      summary = "관리되지 않는 카테고리",
                      value = """
                          {
                            "code": "NOT_MANAGED_CATEGORY",
                            "message": "관리되지 않는 카테고리입니다.",
                            "data": null
                          }
                          """
                  ),
                  @ExampleObject(
                      name = "NOT_FOUND_MEMBER",
                      summary = "미가입 회원",
                      value = """
                          {
                            "code": "NOT_FOUND_MEMBER",
                            "message": "존재하지 않는 회원입니다.",
                            "data": null
                          }
                          """
                  ),
              }
          )
      )
  })
  ApiGlobalResponse<?> modify(
      @RequestBody MemberRequestDto.Modify request
  );
}
