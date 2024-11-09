package com.daejangjangi.backend.newsletter.controller;

import com.daejangjangi.backend.category.domain.Category;
import com.daejangjangi.backend.category.service.CategoryService;
import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.newsletter.domain.dto.NewsletterRequestDto;
import com.daejangjangi.backend.newsletter.domain.dto.NewsletterResponseDto;
import com.daejangjangi.backend.newsletter.domain.dto.NewsletterResponseDto.NewsletterInfoList;
import com.daejangjangi.backend.newsletter.domain.entity.Newsletter;
import com.daejangjangi.backend.newsletter.domain.mapper.NewsletterMapper;
import com.daejangjangi.backend.newsletter.service.NewsletterService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils.Null;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/newsletters")
@RequiredArgsConstructor
public class NewsletterController implements NewsletterApi {

  private final NewsletterService newsletterService;
  private final CategoryService categoryService;

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ApiGlobalResponse<Null> register(
      @Valid @RequestPart NewsletterRequestDto.Register request,
      @RequestPart MultipartFile profileImage
  ) {
    Category category = categoryService.findByName(request.category());
    Newsletter newsletter = NewsletterMapper.INSTANCE.requestToEntity(request);
    newsletterService.save(newsletter, profileImage, category);
    return ApiGlobalResponse.ok();
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping("/{newsletterId}")
  public ApiGlobalResponse<Null> remove(
      @PathVariable("newsletterId") Long newsletterId
  ) {
    Newsletter newsletter = newsletterService.findById(newsletterId);
    newsletterService.remove(newsletter);
    return ApiGlobalResponse.ok();
  }

  @PreAuthorize("hasAuthority('MEMBER')")
  @GetMapping
  public ApiGlobalResponse<NewsletterResponseDto.NewsletterInfoList> list() {
    List<NewsletterResponseDto.NewsletterInfo> newsletterInfoList = newsletterService.findAll();
    NewsletterResponseDto.NewsletterInfoList response = new NewsletterInfoList(newsletterInfoList);
    return ApiGlobalResponse.ok(response);
  }

  @PreAuthorize("hasAuthority('MEMBER')")
  @GetMapping("/{newsletterId}")
  public ApiGlobalResponse<NewsletterResponseDto.NewsletterInfo> info(
      @PathVariable("newsletterId") Long newsletterId
  ) {
    Newsletter newsletter = newsletterService.findById(newsletterId);
    NewsletterResponseDto.NewsletterInfo response
        = NewsletterMapper.INSTANCE.entityToResponse(newsletter);
    return ApiGlobalResponse.ok(response);
  }
}
