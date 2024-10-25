package com.daejangjangi.backend.social.controller;

import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.member.service.MemberService;
import com.daejangjangi.backend.social.domain.SocialMapper;
import com.daejangjangi.backend.social.domain.dto.SocialRequestDto;
import com.daejangjangi.backend.social.domain.dto.SocialResponseDto;
import com.daejangjangi.backend.social.service.SocialService;
import com.daejangjangi.backend.token.domain.dto.TokenResponseDto;
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
public class SocialController implements SocialApi {

  private final SocialService socialService;
  private final MemberService memberService;

  @PostMapping("/login")
  public ApiGlobalResponse<SocialResponseDto.SocialLogin> socialLogin(
      @Valid @RequestBody SocialRequestDto.SocialLogin request) {
    String email = request.email();
    String snsId = request.snsId();
    String provider = request.provider();
    TokenResponseDto tokenDto = socialService.checkSocialAccountLinkage(snsId, provider);
    if (Objects.isNull(tokenDto)) {
      Member member = memberService.findByEmail(email);
      tokenDto = socialService.linkAccount(snsId, provider, member);
    }
    SocialResponseDto.SocialLogin response = SocialMapper.INSTANCE.dtoToResponse(tokenDto);
    return ApiGlobalResponse.ok(response);
  }
}