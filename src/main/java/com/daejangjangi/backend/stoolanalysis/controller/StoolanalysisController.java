package com.daejangjangi.backend.stoolanalysis.controller;

import com.daejangjangi.backend.global.common.PageFields;
import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.member.service.MemberService;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisRequestDto;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisResponseDto.StoolDiagnosticDetailInfo;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisResponseDto.StoolDiagnosticInfo;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisResponseDto.StoolDiagnosticInfos;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisResponseDto.StoolDiagnosticResult;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisResponseDto.StoolImageAnalysis;
import com.daejangjangi.backend.stoolanalysis.domain.entity.StoolDiagnosis;
import com.daejangjangi.backend.stoolanalysis.domain.mapper.StoolDiagnosisMapper;
import com.daejangjangi.backend.stoolanalysis.service.StoolanalysisService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @PreAuthorize("hasAuthority('MEMBER')")
  @GetMapping("/{diagnosisId}")
  public ApiGlobalResponse<StoolDiagnosticDetailInfo> getStoolInfo(
      @PathVariable("diagnosisId") Long diagnosisId) {
    StoolDiagnosis stoolDiagnosis = stoolanalysisService.getStoolInfo(diagnosisId);
    StoolDiagnosticDetailInfo response = StoolDiagnosisMapper.INSTANCE.stoolDiagnosisToStoolDiagnosticDetailInfo(
        stoolDiagnosis);
    return ApiGlobalResponse.ok(response);
  }

  @PreAuthorize("hasAuthority('MEMBER')")
  @GetMapping()
  public ApiGlobalResponse<StoolDiagnosticInfos> getStoolInfos(
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "1") int page) {
    Member member = memberService.info();
    Pageable pageable = PageRequest.of(page - 1, size, Direction.ASC, "stoolAt");
    Page<StoolDiagnosis> stoolDiagnoses = stoolanalysisService.getStoolInfos(member, pageable);
    List<StoolDiagnosticInfo> infos = StoolDiagnosisMapper.INSTANCE.entityToResponse(
        stoolDiagnoses.getContent());

    PageFields pageFields = new PageFields(
        (stoolDiagnoses.getTotalElements() == 0) ? 0 : stoolDiagnoses.getNumber() + 1,
        stoolDiagnoses.getSize(),
        stoolDiagnoses.getTotalElements(), stoolDiagnoses.getTotalPages());
    StoolDiagnosticInfos response = new StoolDiagnosticInfos(infos, pageFields);
    return ApiGlobalResponse.ok(response);
  }

}
