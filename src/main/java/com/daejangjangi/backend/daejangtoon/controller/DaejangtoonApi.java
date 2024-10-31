package com.daejangjangi.backend.daejangtoon.controller;

import com.daejangjangi.backend.daejangtoon.domain.dto.DaejangtoonRequestDto;
import com.daejangjangi.backend.daejangtoon.domain.dto.DaejangtoonResponseDto;
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
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.apache.commons.lang3.ObjectUtils.Null;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Daejangtoon (대장툰) API", description = "대장툰 관련 API")
@ResponseCommonWithSwagger
public interface DaejangtoonApi {

  @Operation(summary = "대장툰 등록", tags = {"Daejangtoon (대장툰) API"},
      responses = {
          @ApiResponse(
              responseCode = "200", description = "OK",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ApiGlobalResponse.class),
                  examples = {
                      @ExampleObject(
                          value = """
                              {
                              "code" : "OK",
                              "message" : "OK",
                              "data" : 1
                              }
                              """
                      )
                  }
              )
          ),
          @ApiResponse(
              responseCode = "400",
              description = "잘못된 요청",
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
                                  "overview : 개요를 입력해주세요.",
                                  "overview : 개요는 최대 250자 이하이어야 합니다.",
                                  "yoil : 연재 요일을 입력하세요.",
                                  "yoil : 지원하지 않는 연재 요일입니다."
                                ]
                              }
                              """
                      )
                  }
              )
          )
      })
  @Response401WithSwagger
  @Response403WithSwagger
  ApiGlobalResponse<Long> register(@Parameter DaejangtoonRequestDto.Register request);

  @Operation(summary = "대장툰 회차 등록", tags = {"Daejangtoon (대장툰) API"})
  @Response200WithSwagger
  @Response401WithSwagger
  @Response403WithSwagger
  @ApiResponse(
      responseCode = "400",
      description = "잘못된 요청",
      content = @Content(
          mediaType = "application/json",
          array = @ArraySchema(schema = @Schema(implementation = ApiGlobalResponse.class)),
          examples = {@ExampleObject(
              value = """
                  {
                    "code" : "BAD_REQUEST",
                    "message" : "잘못된 요청입니다.",
                    "data" : [
                      "chapter : 회차를 입력해주세요.",
                      "chapter : 회차는 최소 1 이상이어야 합니다.",
                      "title : 제목을 입력해주세요.",
                      "title : 제목은 최대 20자 이하이어야 합니다."
                    ]
                  }
                  """
          )
          }
      )
  )
  ApiGlobalResponse<Null> registerChapter(
      @Parameter Long daejangtoonId,
      @RequestBody DaejangtoonRequestDto.RegisterChapter request,
      @RequestBody MultipartFile profileImage,
      @RequestBody List<MultipartFile> toonImages
  );

  @Operation(summary = "대장툰 제거", tags = {"Daejangtoon (대장툰) API"},
      responses = {
          @ApiResponse(
              responseCode = "400",
              description = "잘못된 요청",
              content = @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = ApiGlobalResponse.class)),
                  examples = {
                      @ExampleObject(
                          name = "NOT_FOUND_TOON",
                          value = """
                              {
                              "code" : "NOT_FOUND_TOON",
                              "message" : "존재하지 않는 툰입니다.",
                              "data" : null
                              }
                              """
                      ),
                      @ExampleObject(
                          name = "NOT_FOUND_CHAPTER",
                          value = """
                              {
                              "code" : "NOT_FOUND_CHAPTER",
                              "message" : "존재하지 않는 회차입니다.",
                              "data" : null
                              }
                              """
                      )}
              )
          )
      })
  @Response200WithSwagger
  @Response401WithSwagger
  @Response403WithSwagger
  ApiGlobalResponse<Null> remove(
      @Parameter Long daejangtoonId,
      @Parameter Integer chapter
  );

  @Operation(summary = "대장툰 조회", tags = {"Daejangtoon (대장툰) API"},
      responses = {
          @ApiResponse(
              responseCode = "200", description = "OK",
              content = @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = DaejangtoonResponseDto.Daejangtoon.class))
              )
          ),
          @ApiResponse(
              responseCode = "400",
              description = "잘못된 요청",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ApiGlobalResponse.class),
                  examples = {
                      @ExampleObject(
                          name = "NOT_FOUND_TOON",
                          value = """
                              {
                              "code" : "NOT_FOUND_TOON",
                              "message" : "존재하지 않는 툰입니다.",
                              "data" : null
                              }
                              """
                      )}
              )
          )
      })
  @Response401WithSwagger
  @Response403WithSwagger
  ApiGlobalResponse<DaejangtoonResponseDto.Daejangtoon> info(
      @Parameter Long daejangtoonId
  );

  @Operation(summary = "최신 대장툰 조회", tags = {"Daejangtoon (대장툰) API"},
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "OK",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = DaejangtoonResponseDto.DaejangtoonChapters.class)
              )
          ),
          @ApiResponse(
              responseCode = "400",
              description = "잘못된 요청",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ApiGlobalResponse.class),
                  examples = {
                      @ExampleObject(
                          name = "NOT_FOUND_TOON",
                          value = """
                              {
                              "code" : "NOT_FOUND_TOON",
                              "message" : "존재하지 않는 툰입니다.",
                              "data" : null
                              }
                              """
                      )}
              )
          )
      })
  @Response401WithSwagger
  @Response403WithSwagger
  ApiGlobalResponse<DaejangtoonResponseDto.DaejangtoonChapters> recentDaejangtoon(
      @Parameter Long daejangtoonId
  );

  @Operation(summary = "회차 조회", tags = {"Daejangtoon (대장툰) API"},
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "OK",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = DaejangtoonResponseDto.DaejangtoonChapter.class)
              )
          ),
          @ApiResponse(
              responseCode = "400",
              description = "잘못된 요청",
              content = @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = ApiGlobalResponse.class)),
                  examples = {
                      @ExampleObject(
                          name = "NOT_FOUND_TOON",
                          value = """
                              {
                              "code" : "NOT_FOUND_TOON",
                              "message" : "존재하지 않는 툰입니다.",
                              "data" : null
                              }
                              """
                      ),
                      @ExampleObject(
                          name = "NOT_FOUND_CHAPTER",
                          value = """
                              {
                              "code" : "NOT_FOUND_CHAPTER",
                              "message" : "존재하지 않는 회차입니다.",
                              "data" : null
                              }
                              """
                      )}
              )
          )
      }
  )
  @Response401WithSwagger
  @Response403WithSwagger
  ApiGlobalResponse<DaejangtoonResponseDto.DaejangtoonChapter> chapter(
      @Parameter Long daejangtoonId,
      @Parameter Integer chapter
  );
}
