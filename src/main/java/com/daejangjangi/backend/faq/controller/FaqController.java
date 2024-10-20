package com.daejangjangi.backend.faq.controller;


import com.daejangjangi.backend.faq.domain.dto.FaqRequestDto;
import com.daejangjangi.backend.faq.domain.dto.FaqResponseDto;
import com.daejangjangi.backend.faq.domain.entity.Faq;
import com.daejangjangi.backend.faq.domain.mapper.FaqMapper;
import com.daejangjangi.backend.faq.service.FaqService;
import com.daejangjangi.backend.faq.service.FaqValidator;
import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.member.service.MemberService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
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

  @PreAuthorize("hasAuthority('MEMBER')")
  @GetMapping
  public ApiGlobalResponse<?> faqs() {
    List<Faq> faqs = faqService.faqs();
    List<FaqResponseDto.Faqs> response = FaqMapper.INSTANCE.entityToFaqsResponse(faqs);
    return ApiGlobalResponse.ok(response);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping("/answer")
  public ApiGlobalResponse<?> answer(@Valid @RequestBody FaqRequestDto.Answer request) {
    Long faqId = request.id();
    String answer = request.answer();
    Faq faq = faqService.findById(faqId);
    faqService.updateAnswer(faq, answer);
    return ApiGlobalResponse.ok();
  }
}
