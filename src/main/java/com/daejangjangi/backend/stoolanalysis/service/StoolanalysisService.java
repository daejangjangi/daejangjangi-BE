package com.daejangjangi.backend.stoolanalysis.service;

import com.daejangjangi.backend.file.service.FileValidator;
import com.daejangjangi.backend.file.service.S3Manager;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisRequestDto.ImageAnalyze;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisRequestDto.StoolDiagnose;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisResponseDto.ImageInfo;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisResponseDto.StoolDiagnosticResult;
import com.daejangjangi.backend.stoolanalysis.openfeign.StoolanalysisOpenFeign;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class StoolanalysisService {

  private final String STOOL_ROOT_DIRECTORY = "배변/";

  private final FileValidator fileValidator;
  private final S3Manager s3Manager;
  private final StoolanalysisOpenFeign openFeign;


  /**
   * 배변 이미지 분석
   *
   * @param stoolImage 배변 이미지 파일
   * @return ImageInfo
   */
  public ImageInfo analyzeImage(MultipartFile stoolImage) {
    fileValidator.validateImage(stoolImage);
    String imageUrl = s3Manager.upload(STOOL_ROOT_DIRECTORY, stoolImage);
    ImageAnalyze analyzeImage = new ImageAnalyze(imageUrl);
    return openFeign.analyzeImage(analyzeImage);
  }

  /**
   * 배변 진단
   *
   * @param request 배변 정보
   * @return StoolDiagnosticResult
   */
  public StoolDiagnosticResult diagnoseStool(@Valid StoolDiagnose request) {
    return openFeign.diagnosisStool(request);
  }
}
