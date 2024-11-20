package com.daejangjangi.backend.product.controller;

import com.daejangjangi.backend.global.annotation.swagger.Response200WithSwagger;
import com.daejangjangi.backend.global.annotation.swagger.Response401WithSwagger;
import com.daejangjangi.backend.global.annotation.swagger.Response403WithSwagger;
import com.daejangjangi.backend.global.annotation.swagger.ResponseCommonWithSwagger;
import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.product.domain.dto.DiscountRequestDto.DiscountRegister;
import com.daejangjangi.backend.product.domain.dto.ProductRequestDto;
import com.daejangjangi.backend.product.domain.dto.ProductResponseDto;
import com.daejangjangi.backend.product.domain.dto.ProductResponseDto.ProductInfoList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.ObjectUtils.Null;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Product (상품) API", description = "상품 관련 API")
@ResponseCommonWithSwagger
public interface ProductApi {

  @Operation(summary = "상품 등록", tags = {"Product (상품) API"},
      responses = {
          @ApiResponse(
              responseCode = "400",
              description = "잘못된 요청",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ApiGlobalResponse.class),
                  examples = {
                      @ExampleObject(
                          name = "BAD_REQUEST",
                          summary = "잘못된 요청",
                          value = """
                              "code": "BAD_REQUEST",
                              "message": "잘못된 요청입니다.",
                              "data": [
                                 "name : 상품명을 입력하세요.",
                                 "comment : 상품 코멘트를 입력해주세요.",
                                 "saleLink : 상품 판매 링크를 입력해주세요.",
                                 "regularPrice : 상품 가격을 입력해주세요."
                              ]
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
  ApiGlobalResponse<Null> register(
      @Parameter ProductRequestDto.Register request,
      @Parameter MultipartFile profileImage
  );

  @Operation(summary = "상품 할인 등록", tags = {"Product (상품) API"},
      responses = {
          @ApiResponse(
              responseCode = "400",
              description = "잘못된 요청",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ApiGlobalResponse.class),
                  examples = {
                      @ExampleObject(
                          name = "BAD_REQUEST",
                          summary = "잘못된 요청",
                          value = """
                              "code": "BAD_REQUEST",
                              "message": "잘못된 요청입니다.",
                              "data": [
                                 "name : 할인명을 입력하세요.",
                                 "rate : 할인율을 입력하세요.",
                                 "rate : 할인울은 최대 100이하 입니다.",
                                 "rate : 할인율은 최소 1이상 입니다."
                              ]
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
  ApiGlobalResponse<Null> discount(
      @Parameter Long productId,
      @Parameter DiscountRegister request
  );

  @Operation(summary = "추천 상품 조회", tags = {"Product (상품) API"},
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "OK",
              content = @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = ProductResponseDto.RecommendedProductList.class))
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
                          name = "NOT_FOUND_MEMBER",
                          summary = "미가입 회원",
                          value = """
                              {
                                "code": "NOT_FOUND_MEMBER",
                                "message": "존재하지 않는 회원입니다.",
                                "data": null
                              }"""
                      )
                  }
              )
          )
      }
  )
  @Response401WithSwagger
  @Response403WithSwagger
  ApiGlobalResponse<ProductResponseDto.RecommendedProductList> recommend(@RequestParam int count);

  @Operation(summary = "상품 좋아요", tags = {"Product (상품) API"},
      responses = {
          @ApiResponse(
              responseCode = "400",
              description = "잘못된 요청",
              content = @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = ApiGlobalResponse.class)),
                  examples = {
                      @ExampleObject(
                          name = "NOT_FOUND_MEMBER",
                          summary = "미가입 회원",
                          value = """
                              {
                                "code": "NOT_FOUND_MEMBER",
                                "message": "존재하지 않는 회원입니다.",
                                "data": null
                              }"""
                      ),
                      @ExampleObject(
                          name = "NOT_FOUND_PRODUCT",
                          value = """
                              {
                              "code" : "NOT_FOUND_PRODUCT",
                              "message" : "존재하지 않는 상품입니다.",
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
  ApiGlobalResponse<Null> likeProduct(
      @Parameter Long productId
  );

  @Operation(summary = "관심 상품 목록 조회", tags = {"Product (상품) API"},
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "OK",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ProductInfoList.class)
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
                          name = "NOT_FOUND_MEMBER",
                          summary = "미가입 회원",
                          value = """
                              {
                                "code": "NOT_FOUND_MEMBER",
                                "message": "존재하지 않는 회원입니다.",
                                "data": null
                              }"""
                      ),
                  }
              )
          )
      }
  )
  @Response401WithSwagger
  @Response403WithSwagger
  ApiGlobalResponse<ProductInfoList> myProductLikeList(
      @Parameter int page,
      @Parameter int size
  );
}