package com.daejangjangi.backend.stoolanalysis.controller;

import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.member.service.MemberService;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisRequestDto;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisResponseDto.StoolDiagnosticAiResult;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisResponseDto.StoolDiagnosticResult;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisResponseDto.StoolImageAnalysis;
import com.daejangjangi.backend.stoolanalysis.domain.entity.StoolDiagnosis;
import com.daejangjangi.backend.stoolanalysis.domain.mapper.StoolDiagnosisMapper;
import com.daejangjangi.backend.stoolanalysis.service.StoolanalysisService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
  @PostMapping("/diagnosis")
  public ApiGlobalResponse<StoolDiagnosticResult> diagnose(
      @Valid @RequestBody StoolanalysisRequestDto.StoolDiagnose request) {
    StoolDiagnosticAiResult aiResult = stoolanalysisService.diagnoseStool(request);
    StoolDiagnosticResult response = StoolDiagnosisMapper.INSTANCE.stoolDiagnosticAiResultToResponse(
        request.stools().get(0), aiResult);
    return ApiGlobalResponse.ok(response);
  }

  @PreAuthorize("hasAuthority('MEMBER')")
  @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ApiGlobalResponse<Null> register(
      @Valid @RequestPart StoolanalysisRequestDto.Register request,
      @RequestPart(required = false) List<MultipartFile> stoolImages) {
    Member member = memberService.info();
    StoolDiagnosis stoolDiagnosis = StoolDiagnosisMapper.INSTANCE.requestToEntity(request,
        request.stoolDiagnose().stools().get(0), member);
    stoolanalysisService.register(stoolDiagnosis, stoolImages, request.stoolImageUrl());
    return ApiGlobalResponse.ok();
  }

}
