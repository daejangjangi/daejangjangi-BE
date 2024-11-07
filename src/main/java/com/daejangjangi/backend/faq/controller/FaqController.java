package com.daejangjangi.backend.faq.controller;


import com.daejangjangi.backend.faq.domain.dto.FaqRequestDto;
import com.daejangjangi.backend.faq.domain.dto.FaqResponseDto;
import com.daejangjangi.backend.faq.domain.dto.FaqResponseDto.FaqList;
import com.daejangjangi.backend.faq.domain.entity.Faq;
import com.daejangjangi.backend.faq.service.FaqService;
import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.qna.domain.entity.Qna;
import com.daejangjangi.backend.qna.service.QnaService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils.Null;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/faqs")
@RequiredArgsConstructor
public class FaqController implements FaqApi {

  private final QnaService qnaService;
  private final FaqService faqService;

  @PreAuthorize("hasAuthority('MEMBER')")
  @PostMapping
  public ApiGlobalResponse<Null> register(
      @Valid @RequestBody FaqRequestDto.Register request
  ) {
    Long qnaId = request.id();
    Qna qna = qnaService.findById(qnaId);
    faqService.save(qna);
    return ApiGlobalResponse.ok();
  }

  @PreAuthorize("hasAuthority('MEMBER')")
  @GetMapping
  public ApiGlobalResponse<FaqResponseDto.FaqList> faqs() {
    List<FaqResponseDto.FaqDto> faqDtoList = faqService.findAll();
    FaqResponseDto.FaqList response = new FaqList(faqDtoList);
    return ApiGlobalResponse.ok(response);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping("/{faqId}")
  public ApiGlobalResponse<Null> remove(
      @PathVariable("faqId") Long faqId
  ) {
    Faq faq = faqService.findById(faqId);
    faqService.remove(faq);
    return ApiGlobalResponse.ok();
  }
}
