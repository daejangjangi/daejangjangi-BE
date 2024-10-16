package com.daejangjangi.backend.social.controller;

import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.member.service.MemberService;
import com.daejangjangi.backend.social.domain.dto.OAuthRequestDto;
import com.daejangjangi.backend.social.service.SocialService;
import com.daejangjangi.backend.social.service.SocialValidator;
import com.daejangjangi.backend.token.domain.dto.TokenDto;
import jakarta.validation.Valid;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/socials")
@RequiredArgsConstructor
public class SocialController {

  private final SocialService socialService;
  private final SocialValidator socialValidator;
  private final MemberService memberService;

  @PostMapping("/login")
  public ApiGlobalResponse<?> socialLogin(@Valid @RequestBody OAuthRequestDto.SocialLogin request) {
    String email = request.email();
    String snsId = request.snsId();
    String provider = request.provider().toUpperCase();
    socialValidator.checkSocialAccountProvider(request.provider());
    TokenDto response = socialService.checkSocialAccountLinkage(snsId, provider);
    if (Objects.isNull(response)) {
      Member member = memberService.findByEmail(email);
      response = socialService.linkAccount(snsId, provider, member);
    }
    return ApiGlobalResponse.ok(response);
  }
}