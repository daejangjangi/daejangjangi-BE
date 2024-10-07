package com.daejangjangi.backend.member.controller;

import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.member.domain.Member;
import com.daejangjangi.backend.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

  private final MemberService memberService;

  @GetMapping("/email/check/{email}")
  public ApiGlobalResponse<?> emailDuplicationCheck(@PathVariable("email") String email) {
    memberService.checkEmail(email);
    return ApiGlobalResponse.ok();
  }

  @GetMapping("/nickname/check/{nickname}")
  public ApiGlobalResponse<?> nicknameDuplicationCheck(@PathVariable("nickname") String nickname) {
    memberService.checkNickname(nickname);
    return ApiGlobalResponse.ok();
  }
}
