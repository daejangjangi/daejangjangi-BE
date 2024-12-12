package com.daejangjangi.backend.stoolanalysis.controller;

import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.member.service.MemberService;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisRequestDto;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisResponseDto.StoolDiagnosticResult;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisResponseDto.StoolImageAnalysis;
import com.daejangjangi.backend.stoolanalysis.service.StoolanalysisService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  public ApiGlobalResponse<StoolImageAnalysis> analyzeImage(
      @RequestPart MultipartFile stoolImage) {
    return ApiGlobalResponse.ok(stoolanalysisService.analyzeImage(stoolImage));
  }

  @PreAuthorize("hasAuthority('MEMBER')")
  @PostMapping(value = "/diagnosis", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ApiGlobalResponse<StoolDiagnosticResult> diagnose(
      @Valid @RequestPart StoolanalysisRequestDto.StoolDiagnose request,
      @RequestPart(required = false) List<MultipartFile> stoolImages) {
    Member member = memberService.info();
    return ApiGlobalResponse.ok(stoolanalysisService.diagnoseStool(request, stoolImages, member));
  }

  @PreAuthorize("hasAuthority('MEMBER')")
  @PostMapping("/{diagnosisId}")
  public ApiGlobalResponse<Null> register(@PathVariable("diagnosisId") Long diagnosisId) {
    Member member = memberService.info();
    stoolanalysisService.register(member, diagnosisId);
    return ApiGlobalResponse.ok();
  }

  @PreAuthorize("hasAuthority('MEMBER')")
  @DeleteMapping("/{diagnosisId}")
  public ApiGlobalResponse<Null> remove(@PathVariable("diagnosisId") Long diagnosisId) {
    Member member = memberService.info();
    stoolanalysisService.remove(member, diagnosisId);
    return ApiGlobalResponse.ok();
  }

}
