package com.daejangjangi.backend.stoolanalysis.service;

import com.daejangjangi.backend.file.service.FileValidator;
import com.daejangjangi.backend.file.service.S3Manager;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisRequestDto.ImageAnalyzeAi;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisRequestDto.StoolDiagnose;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisRequestDto.StoolDiagnoseAi;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisResponseDto.StoolDiagnosticAiResult;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisResponseDto.StoolDiagnosticResult;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisResponseDto.StoolImageAiAnalysis;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisResponseDto.StoolImageAnalysis;
import com.daejangjangi.backend.stoolanalysis.domain.entity.StoolDiagnosis;
import com.daejangjangi.backend.stoolanalysis.domain.entity.StoolImage;
import com.daejangjangi.backend.stoolanalysis.domain.mapper.StoolDiagnosisMapper;
import com.daejangjangi.backend.stoolanalysis.exception.NotFoundStoolDiagnosisException;
import com.daejangjangi.backend.stoolanalysis.exception.NotFoundStoolImageException;
import com.daejangjangi.backend.stoolanalysis.exception.UnauthorizedStoolException;
import com.daejangjangi.backend.stoolanalysis.openfeign.StoolanalysisOpenFeign;
import com.daejangjangi.backend.stoolanalysis.repository.StoolDiagnosisRepository;
import com.daejangjangi.backend.stoolanalysis.repository.StoolImageRepository;
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


  /**
   * 배변 이미지 분석
   *
   * @param stoolImage 배변 이미지 파일
   * @return ImageInfo
   */
  @Transactional
  public StoolImageAnalysis analyzeImage(MultipartFile stoolImage) {
    String imageUrl = saveStoolImage(stoolImage);
    ImageAnalyzeAi analyzeImage = new ImageAnalyzeAi(imageUrl);
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
  @Transactional
  public StoolDiagnosticResult diagnoseStool(StoolDiagnose request,
      List<MultipartFile> stoolImages, Member member) {
    StoolDiagnosticAiResult aiResult = openFeign.diagnosisStool(request.stoolDiagnose());

    StoolDiagnosis stoolDiagnosis = saveStoolDiagnosis(request.stoolDiagnose(), member,
        aiResult.userLanguage());

    StoolImage stoolImage = stoolimageRepository.findByStoolImage(request.stoolImageUrl())
        .orElseThrow(NotFoundStoolImageException::new);
    stoolImage.updateStoolDiagnosis(stoolDiagnosis);

    if (!Objects.isNull(stoolImages)) {
      saveStoolImages(stoolImages, stoolDiagnosis);
    }

    return StoolDiagnosisMapper.INSTANCE.stoolDiagnosticAiResultToResponse(
        request.stoolDiagnose().stools().get(0),
        aiResult.userLanguage());
  }

  /**
   * 배변 분석 결과 저장
   *
   * @param member      회원 정보
   * @param diagnosisId 배변 진단 정보
   */
  @Transactional
  public void register(Member member, Long diagnosisId) {
    StoolDiagnosis stoolDiagnosis = findById(diagnosisId);
    validAuthor(member, stoolDiagnosis);
    stoolDiagnosis.updateResultSaved();
  }

  /**
   * 배변 진단 결과 삭제
   *
   * @param member      로그인 정보
   * @param diagnosisId 배변 진단 id
   */
  @Transactional
  public void remove(Member member, Long diagnosisId) {
    StoolDiagnosis stoolDiagnosis = findById(diagnosisId);
    validAuthor(member, stoolDiagnosis);
    stooldiagnosisRepository.delete(stoolDiagnosis);
  }

  /**
   * id로 배변 진단 찾기
   *
   * @param diagnosisId 배변 진단 id
   * @return StoolDiagnosis
   */
  private StoolDiagnosis findById(Long diagnosisId) {
    return stooldiagnosisRepository.findById(diagnosisId)
        .orElseThrow(NotFoundStoolDiagnosisException::new);
  }

  /**
   * 배변 진단 데이터 권한 확인
   *
   * @param member         회원 정보
   * @param stoolDiagnosis 배변 진단 id
   */
  private void validAuthor(Member member, StoolDiagnosis stoolDiagnosis) {
    if (!Objects.equals(stoolDiagnosis.getMember(), member)) {
      throw new UnauthorizedStoolException();
    }
  }

  /**
   * 배변 이미지 분석에 사용할 이미지 s3 및 DB 저장
   *
   * @param stoolImage 배변 이미지 png 파일
   * @return String
   */
  private String saveStoolImage(MultipartFile stoolImage) {
    fileValidator.validateImage(stoolImage);
    String imageUrl = s3Manager.upload(STOOL_ROOT_DIRECTORY, stoolImage);
    StoolImage savedStoolImage = stoolimageRepository.save(StoolImage.builder()
        .stoolImage(imageUrl).build());
    return savedStoolImage.getStoolImage();
  }

  /**
   * 여러 배변 이미지 s3 및 DB 저장
   *
   * @param stoolImages    배변 이미지 png 파일들
   * @param stoolDiagnosis 배변 진단 정보
   */
  private void saveStoolImages(List<MultipartFile> stoolImages,
      StoolDiagnosis stoolDiagnosis) {
    List<StoolImage> addImages = new ArrayList<>();
    fileValidator.validateImages(stoolImages);
    List<String> imageUrls = s3Manager.uploadWithoutOrder(STOOL_ROOT_DIRECTORY, stoolImages);

    for (String imageUrl : imageUrls) {
      addImages.add(StoolImage.builder()
          .stoolImage(imageUrl)
          .stooldiagnosis(stoolDiagnosis)
          .build());
    }
    List<StoolImage> saveImages = stoolimageRepository.saveAll(addImages);
    stoolDiagnosis.addStoolImages(saveImages);
  }

  /**
   * 배변 진단 정보 저장
   *
   * @param stoolDiagnose    배변 진단 AI 요청 정보
   * @param member           회원 정보
   * @param diagnosticResult 배변 진단 결과
   * @return
   */
  private StoolDiagnosis saveStoolDiagnosis(StoolDiagnoseAi stoolDiagnose, Member member,
      String diagnosticResult) {
    StoolDiagnosis stoolDiagnosis = StoolDiagnosisMapper.INSTANCE.requestToEntity(
        stoolDiagnose, stoolDiagnose.stools().get(0), member, diagnosticResult);
    return stooldiagnosisRepository.save(stoolDiagnosis);
  }


}
