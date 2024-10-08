package com.daejangjangi.backend.member.controller;

import com.daejangjangi.backend.category.domain.entity.Category;
import com.daejangjangi.backend.category.service.CategoryService;
import com.daejangjangi.backend.disease.domain.entity.Disease;
import com.daejangjangi.backend.disease.service.DiseaseService;
import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.member.domain.dto.MemberRequestDto;
import com.daejangjangi.backend.member.service.MemberService;
import jakarta.validation.Valid;
import java.util.List;
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
    Member member = request.toEntity();
    List<Disease> diseases = diseaseService.findByNames(request.diseases());
    List<Category> categories = categoryService.findByNames(request.categories());
    memberService.save(member, diseases, categories);
    return ApiGlobalResponse.ok();
  }
}
