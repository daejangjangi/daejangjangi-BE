package com.daejangjangi.backend.stoolanalysis.service;

import com.daejangjangi.backend.file.service.FileValidator;
import com.daejangjangi.backend.file.service.S3Manager;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisRequestDto.ImageAnalyze;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisRequestDto.StoolDiagnose;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisResponseDto.StoolDiagnosticAiResult;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisResponseDto.StoolImageAiAnalysis;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisResponseDto.StoolImageAnalysis;
import com.daejangjangi.backend.stoolanalysis.domain.entity.StoolDiagnosis;
import com.daejangjangi.backend.stoolanalysis.domain.entity.StoolImage;
import com.daejangjangi.backend.stoolanalysis.domain.mapper.StoolDiagnosisMapper;
import com.daejangjangi.backend.stoolanalysis.openfeign.StoolanalysisOpenFeign;
import com.daejangjangi.backend.stoolanalysis.repository.StoolDiagnosisRepository;
import com.daejangjangi.backend.stoolanalysis.repository.StoolImageRepository;
import com.daejangjangi.backend.stoollog.domain.entity.Stoollog;
import com.daejangjangi.backend.stoollog.repository.StoollogRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class StoolanalysisService {

  private final String STOOL_ROOT_DIRECTORY = "배변/";

  private final FileValidator fileValidator;
  private final S3Manager s3Manager;
  private final StoolanalysisOpenFeign openFeign;
  private final StoolDiagnosisRepository stooldiagnosisRepository;
  private final StoolImageRepository stoolimageRepository;
  private final StoollogRepository stoollogRepository;


  /**
   * 배변 이미지 분석
   *
   * @param stoolImage 배변 이미지 파일
   * @return ImageInfo
   */
  public StoolImageAnalysis analyzeImage(MultipartFile stoolImage) {
    fileValidator.validateImage(stoolImage);
    String imageUrl = s3Manager.upload(STOOL_ROOT_DIRECTORY, stoolImage);
    ImageAnalyze analyzeImage = new ImageAnalyze(imageUrl);
    StoolImageAiAnalysis stoolImageAiAnalysis = openFeign.analyzeImage(analyzeImage);
    return StoolDiagnosisMapper.INSTANCE.stoolImageAnalysisToResponse(stoolImageAiAnalysis,
        imageUrl);
  }

  /**
   * 배변 진단
   *
   * @param request 배변 정보
   * @return StoolDiagnosticResult
   */
  public StoolDiagnosticAiResult diagnoseStool(StoolDiagnose request) {
    return openFeign.diagnosisStool(request);
  }

  /**
   * 배변 분석 결과 일지에 저장
   *
   * @param stoolDiagnosis 배변 분석 결과 정보
   * @param stoolImages    추가적인 배변 이미지 파일들
   * @param analyzedImage  분석된 이미지 url
   */
  @Transactional
  public void register(StoolDiagnosis stoolDiagnosis, List<MultipartFile> stoolImages,
      String analyzedImage) {
    Stoollog stoollog = Stoollog.builder()
        .color(stoolDiagnosis.getColor())
        .loggedAt(stoolDiagnosis.getStoolAt())
        .form(stoolDiagnosis.getForm()).build();
    stoollog.updateMember(stoolDiagnosis.getMember());
    stoollog = stoollogRepository.save(stoollog);
    stoolDiagnosis.updateStoollog(stoollog);
    stoolDiagnosis = stooldiagnosisRepository.save(stoolDiagnosis);

    List<StoolImage> addImages = new ArrayList<>();
    addImages.add(StoolImage.builder()
        .stoolImage(analyzedImage)
        .stooldiagnosis(stoolDiagnosis)
        .build());

    if (!Objects.isNull(stoolImages)) {
      fileValidator.validateImages(stoolImages);
      List<String> imageUrls = s3Manager.uploadWithoutOrder(STOOL_ROOT_DIRECTORY, stoolImages);

      for (String imageUrl : imageUrls) {
        addImages.add(StoolImage.builder()
            .stoolImage(imageUrl)
            .stooldiagnosis(stoolDiagnosis)
            .build());
      }
    }

    List<StoolImage> saveImages = stoolimageRepository.saveAll(addImages);
    stoolDiagnosis.addStoolImages(saveImages);
  }
}
