package com.daejangjangi.backend.stoolanalysis.controller;

import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.member.service.MemberService;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisResponseDto.ImageInfo;
import com.daejangjangi.backend.stoolanalysis.service.StoolanalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/stoolanalyses")
@RequiredArgsConstructor
public class StoolanalysisController implements StoolanalysisApi {

  private final MemberService memberService;
  private final StoolanalysisService stoolanalysisService;

  @PreAuthorize("hasAuthority('MEMBER')")
  @PostMapping(value = "/image", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ApiGlobalResponse<ImageInfo> analyzeImage(
      @RequestPart MultipartFile stoolImage) {
    return ApiGlobalResponse.ok(stoolanalysisService.analyzeImage(stoolImage));
  }


}
