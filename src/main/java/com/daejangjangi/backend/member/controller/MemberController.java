package com.daejangjangi.backend.member.controller;

import com.daejangjangi.backend.category.domain.Category;
import com.daejangjangi.backend.category.service.CategoryService;
import com.daejangjangi.backend.disease.domain.Disease;
import com.daejangjangi.backend.disease.service.DiseaseService;
import com.daejangjangi.backend.global.config.SecurityConfig;
import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.member.domain.dto.MemberRequestDto;
import com.daejangjangi.backend.member.domain.dto.MemberResponseDto;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.member.domain.mapper.MemberMapper;
import com.daejangjangi.backend.member.service.MemberService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController implements MemberApi {

  private final MemberService memberService;
  private final DiseaseService diseaseService;
  private final CategoryService categoryService;

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

  @PostMapping("/join")
  public ApiGlobalResponse<?> join(@Valid @RequestBody MemberRequestDto.Join request) {
    Member member = MemberMapper.INSTANCE.joinRequestToEntity(request);
    List<Disease> diseases = diseaseService.findByNames(request.diseases());
    List<Category> categories = categoryService.findByNames(request.categories());
    memberService.save(member, diseases, categories);
    return ApiGlobalResponse.ok();
  }

  // note : REST_API 로 관리해주기 위해서 forward 하여 filter chain 안으로 넘기기는 했는데, 다른 좋은 방법이 있을까요..?
  @PostMapping("/login")
  public void login(@Valid @RequestBody MemberRequestDto.Login loginRequest,
      HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setAttribute("loginRequest", loginRequest);
    request.getRequestDispatcher(SecurityConfig.FILTER_PROCESS_URL).forward(request, response);
  }

  @PreAuthorize("hasAuthority('MEMBER')")
  @GetMapping("/info")
  public ApiGlobalResponse<?> info() {
    Member member = memberService.info();
    MemberResponseDto.Info response = MemberMapper.INSTANCE.entityToInfoResponse(member);
    return ApiGlobalResponse.ok(response);
  }

  @PreAuthorize("hasAuthority('MEMBER')")
  @PutMapping
  public ApiGlobalResponse<?> modify(@Valid @RequestBody MemberRequestDto.Modify request) {
    Member member = MemberMapper.INSTANCE.modifyRequestToEntity(request);
    List<Disease> diseases = diseaseService.findByNames(request.diseases());
    List<Category> categories = categoryService.findByNames(request.categories());
    memberService.update(member, diseases, categories);
    return ApiGlobalResponse.ok();
  }
}
