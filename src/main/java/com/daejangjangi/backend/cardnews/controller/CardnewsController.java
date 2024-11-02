package com.daejangjangi.backend.cardnews.controller;

import com.daejangjangi.backend.cardnews.domain.dto.CardnewsRequestDto;
import com.daejangjangi.backend.cardnews.domain.entity.Cardnews;
import com.daejangjangi.backend.cardnews.domain.mapper.CardnewsMapper;
import com.daejangjangi.backend.cardnews.service.CardnewsService;
import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils.Null;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/cardnews")
@RequiredArgsConstructor
public class CardnewsController {

  private final CardnewsService cardnewsService;

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ApiGlobalResponse<Null> register(
      @Valid @RequestPart CardnewsRequestDto.Register request,
      @RequestPart MultipartFile profileImage,
      @RequestPart List<MultipartFile> cardnewsImages
  ) {
    Cardnews cardnews = CardnewsMapper.INSTANCE.registerRequestToEntity(request);
    cardnewsService.save(cardnews, profileImage, cardnewsImages);
    return ApiGlobalResponse.ok();
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping("/{cardnewsId}")
  public ApiGlobalResponse<Null> remove(
      @PathVariable Long cardnewsId
  ) {
    Cardnews cardnews = cardnewsService.findById(cardnewsId);
    cardnewsService.remove(cardnews);
    return ApiGlobalResponse.ok();
  }
}
