package com.daejangjangi.backend.qna.controller;

import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.member.service.MemberService;
import com.daejangjangi.backend.qna.domain.entity.Qna;
import com.daejangjangi.backend.qna.domain.mapper.QnaMapper;
import com.daejangjangi.backend.qna.domain.dto.QnaRequestDto;
import com.daejangjangi.backend.qna.domain.dto.QnaResponseDto.InfoList;
import com.daejangjangi.backend.qna.domain.dto.QnaResponseDto.MyInfo;
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
@RequestMapping("/api/v1/qnas")
@RequiredArgsConstructor
public class QnaController implements QnaApi {

  private final MemberService memberService;
  private final QnaService qnaService;

  @PreAuthorize("hasAuthority('MEMBER')")
  @PostMapping
  public ApiGlobalResponse<Null> register(
      @Valid @RequestBody QnaRequestDto.Register request
  ) {
    Member member = memberService.info();
    Qna qna = QnaMapper.INSTANCE.registerRequestToEntity(request);
    qnaService.save(member, qna);
    return ApiGlobalResponse.ok();
  }

  @PreAuthorize("hasAuthority('MEMBER')")
  @DeleteMapping("/{qnaId}")
  public ApiGlobalResponse<Null> remove(
      @PathVariable("qnaId") Long qnaId
  ) {
    Member member = memberService.info();
    Qna qna = qnaService.findById(qnaId);
    memberService.isAuthor(member, qna.getMember());
    qnaService.remove(qna);
    return ApiGlobalResponse.ok();
  }

  @PreAuthorize("hasAuthority('MEMBER')")
  @GetMapping
  public ApiGlobalResponse<InfoList> myQnaList() {
    Member member = memberService.info();
    List<MyInfo> myQnaList = qnaService.findMyQnaList(member);
    InfoList response = new InfoList(myQnaList);
    return ApiGlobalResponse.ok(response);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping("/answer")
  public ApiGlobalResponse<Null> answer(
      @Valid @RequestBody QnaRequestDto.Answer request
  ) {
    Long id = request.id();
    String answer = request.answer();
    Qna qna = qnaService.findById(id);
    qnaService.registerAnswer(qna, answer);
    return ApiGlobalResponse.ok();
  }
}
