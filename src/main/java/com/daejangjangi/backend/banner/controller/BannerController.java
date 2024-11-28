package com.daejangjangi.backend.banner.controller;

import com.daejangjangi.backend.banner.domain.dto.BannerResponseDto.BannerInfo;
import com.daejangjangi.backend.banner.domain.dto.BannerResponseDto.BannerInfoList;
import com.daejangjangi.backend.banner.service.BannerService;
import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.product.domain.entity.Product;
import com.daejangjangi.backend.product.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils.Null;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/banners")
@RequiredArgsConstructor
public class BannerController implements BannerApi {

  private final ProductService productService;
  private final BannerService bannerService;

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ApiGlobalResponse<Null> register(
      @RequestPart MultipartFile bannerImage,
      @RequestPart Long productId
  ) {
    Product product = productService.findById(productId);
    bannerService.save(bannerImage, product);
    return ApiGlobalResponse.ok();
  }

  @PreAuthorize("hasAuthority('MEMBER')")
  @GetMapping
  public ApiGlobalResponse<BannerInfoList> list() {
    List<BannerInfo> bannerInfoList = bannerService.list();
    BannerInfoList response = new BannerInfoList(bannerInfoList);
    return ApiGlobalResponse.ok(response);
  }
}
