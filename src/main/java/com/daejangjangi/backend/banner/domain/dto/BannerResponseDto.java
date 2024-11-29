package com.daejangjangi.backend.banner.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public class BannerResponseDto {

  @Schema(name = "BannerInfoListResponse", description = "배너 목록 응답 DTO")
  public record BannerInfoList(

      @Schema(description = "배너 목록")
      List<BannerInfo> bannerInfoList
  ) {

  }

  @Schema(name = "BannerInfoResponse", description = "배너 응답 DTO")
  public record BannerInfo(

      @Schema(description = "배너 아이디", example = "1")
      Long id,

      @Schema(description = "배너 이미지", example = "배너 이미지 링크")
      String bannerImage,

      @Schema(description = "상품 판매 링크", example = "상품 판매 링크")
      String saleLink
  ) {

  }
}
