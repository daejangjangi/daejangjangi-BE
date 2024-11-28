package com.daejangjangi.backend.fcm.controller;

import com.daejangjangi.backend.fcm.domain.dto.FcmRequestDto;
import com.daejangjangi.backend.fcm.service.FcmService;
import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.member.service.MemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/fcms")
@RequiredArgsConstructor
public class FcmController implements FcmApi {

  private final MemberService memberService;
  private final FcmService fcmService;

  @PostMapping
  @PreAuthorize("hasAuthority('MEMBER')")
  public ApiGlobalResponse<Null> registerFcmtoken(
      @Valid @RequestBody FcmRequestDto.FcmtokenRequest request) {
    Member member = memberService.info();
    fcmService.save(member, request.fcmToken());
    return ApiGlobalResponse.ok();
  }

  @DeleteMapping
  @PreAuthorize("hasAuthority('MEMBER')")
  public ApiGlobalResponse<Null> deleteFcmtoken(
      @Valid @RequestBody FcmRequestDto.FcmtokenRequest request) {
    fcmService.delete(request.fcmToken());
    return ApiGlobalResponse.ok();
  }

}
