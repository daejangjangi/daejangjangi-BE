package com.daejangjangi.backend.product.controller;

import com.daejangjangi.backend.category.domain.Category;
import com.daejangjangi.backend.category.service.CategoryService;
import com.daejangjangi.backend.disease.domain.Disease;
import com.daejangjangi.backend.disease.service.DiseaseService;
import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.member.service.MemberService;
import com.daejangjangi.backend.product.domain.dto.ProductRequestDto;
import com.daejangjangi.backend.product.domain.dto.ProductResponseDto;
import com.daejangjangi.backend.product.domain.dto.ProductResponseDto.RecommendedProductList;
import com.daejangjangi.backend.product.domain.entity.Product;
import com.daejangjangi.backend.product.domain.mapper.ProductMapper;
import com.daejangjangi.backend.product.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils.Null;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController implements ProductApi {

  private final ProductService productService;
  private final MemberService memberService;
  private final CategoryService categoryService;
  private final DiseaseService diseaseService;


  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ApiGlobalResponse<Null> register(
      @Valid @RequestPart ProductRequestDto.Register request,
      @RequestPart MultipartFile profileImage
  ) {
    List<Disease> diseases = diseaseService.findByNames(request.diseases());
    List<Category> categories = categoryService.findByNames(request.categories());
    Product product = ProductMapper.INSTANCE.requestToEntity(request);
    productService.register(product, profileImage, diseases, categories);
    return ApiGlobalResponse.ok();
  }

  @PreAuthorize("hasAuthority('MEMBER')")
  @GetMapping("/recommend")
  public ApiGlobalResponse<ProductResponseDto.RecommendedProductList> recommend(
      @RequestParam int count
  ) {
    Member member = memberService.info();
    List<ProductResponseDto.RecommendedProduct> recommendedProducts
        = productService.getRecommendedProducts(member, count);
    ProductResponseDto.RecommendedProductList response
        = new RecommendedProductList(recommendedProducts);
    return ApiGlobalResponse.ok(response);
  }
}
