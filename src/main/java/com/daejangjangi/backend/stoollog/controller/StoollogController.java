package com.daejangjangi.backend.stoollog.controller;

import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.member.service.MemberService;
import com.daejangjangi.backend.stoollog.domain.dto.StoollogRequestDto;
import com.daejangjangi.backend.stoollog.domain.entity.Stoollog;
import com.daejangjangi.backend.stoollog.domain.mapper.StoollogMapper;
import com.daejangjangi.backend.stoollog.service.StoollogService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stoollogs")
@RequiredArgsConstructor
public class StoollogController implements StoollogAPI {

  private final MemberService memberService;
  private final StoollogService stoolLogService;

  @PostMapping
  @PreAuthorize("hasAuthority('MEMBER')")
  public ApiGlobalResponse<Null> save(
      @Valid @RequestBody StoollogRequestDto.StoollogRegisterRequest request) {
    Member member = memberService.info();
    Stoollog stoollog = StoollogMapper.INSTANCE.registerRequestToEntity(request);
    stoollog.updateMember(member);
    stoolLogService.save(stoollog);
    return ApiGlobalResponse.ok();
  }

  @PutMapping
  @PreAuthorize("hasAuthority('MEMBER')")
  public ApiGlobalResponse<Null> update(
      @Valid @RequestBody StoollogRequestDto.StoollogModifyRequest request) {
    Stoollog stoollog = StoollogMapper.INSTANCE.modifyRequestToEntity(request);
    stoolLogService.update(stoollog);
    return ApiGlobalResponse.ok();
  }

  @DeleteMapping("/{stoollogId}")
  @PreAuthorize("hasAuthority('MEMBER')")
  public ApiGlobalResponse<Null> delete(@PathVariable("stoollogId") Long stoollogId) {
    stoolLogService.delete(stoollogId);
    return ApiGlobalResponse.ok();
  }

}
