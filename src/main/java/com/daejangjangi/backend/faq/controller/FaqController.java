package com.daejangjangi.backend.faq.controller;


import com.daejangjangi.backend.faq.domain.dto.FaqRequestDto;
import com.daejangjangi.backend.faq.domain.entity.Faq;
import com.daejangjangi.backend.faq.domain.mapper.FaqMapper;
import com.daejangjangi.backend.faq.service.FaqService;
import com.daejangjangi.backend.faq.service.FaqValidator;
import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/faqs")
@RequiredArgsConstructor
public class FaqController implements FaqApi {

  private final MemberService memberService;
  private final FaqService faqService;
  private final FaqValidator faqValidator;

  @PreAuthorize("hasAuthority('MEMBER')")
  @PostMapping
  public ApiGlobalResponse<?> register(@Valid @RequestBody FaqRequestDto.Register request) {
    faqValidator.checkFaqCategory(request.category());
    Member member = memberService.info();
    Faq faq = FaqMapper.INSTANCE.registerRequestToEntity(request);
    faqService.save(member, faq);
    return ApiGlobalResponse.ok();
  }
}
