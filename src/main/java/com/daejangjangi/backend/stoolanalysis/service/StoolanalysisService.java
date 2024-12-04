package com.daejangjangi.backend.stoolanalysis.service;

import com.daejangjangi.backend.file.service.FileValidator;
import com.daejangjangi.backend.file.service.S3Manager;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisRequestDto.ImageAnalyze;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisRequestDto.StoolDiagnose;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisResponseDto.ImageInfo;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisResponseDto.StoolDiagnosticResult;
import com.daejangjangi.backend.stoolanalysis.domain.entity.StoolDiagnosis;
import com.daejangjangi.backend.stoolanalysis.domain.entity.StoolImage;
import com.daejangjangi.backend.stoolanalysis.openfeign.StoolanalysisOpenFeign;
import com.daejangjangi.backend.stoolanalysis.repository.StoolDiagnosisRepository;
import com.daejangjangi.backend.stoolanalysis.repository.StoolImageRepository;
import com.daejangjangi.backend.stoollog.domain.entity.Stoollog;
import com.daejangjangi.backend.stoollog.repository.StoollogRepository;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
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
  public ImageInfo analyzeImage(MultipartFile stoolImage) {
    fileValidator.validateImage(stoolImage);
    String imageUrl = s3Manager.upload(STOOL_ROOT_DIRECTORY, stoolImage);
    System.out.println(imageUrl);
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

  /**
   * @param stoolDiagnosis
   * @param stoolImages
   * @param analyzedImage
   */
  @Transactional
  public void register(StoolDiagnosis stoolDiagnosis, List<MultipartFile> stoolImages,
      String analyzedImage) {
    stoolDiagnosis = stooldiagnosisRepository.save(stoolDiagnosis);
    Stoollog stoollog = stoollogRepository.save(Stoollog.builder()
        .color(stoolDiagnosis.getColor())
        .loggedAt(stoolDiagnosis.getStoolAt())
        .form(stoolDiagnosis.getForm()).build());
    stoolDiagnosis.updateStoolog(stoollog);

    List<StoolImage> addImages = new ArrayList<>();
    addImages.add(StoolImage.builder()
        .stooldiagnosis(stoolDiagnosis)
        .stoolImage(analyzedImage)
        .build());

    if (stoolImages != null) {
      fileValidator.validateImages(stoolImages);
      List<String> imageUrls = s3Manager.uploadWithoutOrder(STOOL_ROOT_DIRECTORY, stoolImages);

      for (String imageUrl : imageUrls) {
        addImages.add(StoolImage.builder()
            .stooldiagnosis(stoolDiagnosis)
            .stoolImage(imageUrl)
            .build());
      }
    }

    List<StoolImage> saveImages = stoolimageRepository.saveAll(addImages);
    stoolDiagnosis.addStoolImages(saveImages);

  }

  public void get(Long id) {
    StoolDiagnosis stoolDiagnosis = stooldiagnosisRepository.findById(id).get();
    stoolDiagnosis.getStoollog();

  }
}
