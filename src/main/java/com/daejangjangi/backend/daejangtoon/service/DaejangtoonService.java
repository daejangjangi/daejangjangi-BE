package com.daejangjangi.backend.daejangtoon.service;

import com.daejangjangi.backend.daejangtoon.domain.dto.DaejangtoonImageDto;
import com.daejangjangi.backend.daejangtoon.domain.entity.Daejangtoon;
import com.daejangjangi.backend.daejangtoon.domain.entity.DaejangtoonImage;
import com.daejangjangi.backend.daejangtoon.domain.mapper.DaejangtoonImageMapper;
import com.daejangjangi.backend.daejangtoon.exception.NotFoundToonException;
import com.daejangjangi.backend.daejangtoon.repository.DaejangtoonImageRepository;
import com.daejangjangi.backend.daejangtoon.repository.DaejangtoonRepository;
import com.daejangjangi.backend.file.service.FileValidator;
import com.daejangjangi.backend.file.service.S3Manager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class DaejangtoonService {

  private final String DAEJANGTOON_ROOT_DIRECTORY = "대장툰/";

  private final DaejangtoonRepository daejangtoonRepository;
  private final DaejangtoonImageRepository daejangtoonImageRepository;
  private final FileValidator fileValidator;
  private final S3Manager s3Manager;

  /**
   * 대장툰 등록
   * <p>
   * note : 이미지 - DB 데이터 동기화 관련 고민해보기
   *
   * @param daejangtoon  대장툰
   * @param profileImage 프로필 이미지
   * @param toonImages   컷 이미지 목록
   */
  @Transactional
  public void save(Daejangtoon daejangtoon, MultipartFile profileImage,
      List<MultipartFile> toonImages) {
    fileValidator.validate(profileImage);
    fileValidator.validate(toonImages);
    String profileUrl = s3Manager.upload(profileImage);
    List<DaejangtoonImageDto> images =
        s3Manager.upload(DAEJANGTOON_ROOT_DIRECTORY + daejangtoon.getChapter(), toonImages);
    List<DaejangtoonImage> daejangtoonImages = DaejangtoonImageMapper.INSTANCE.dtoToEntity(images);
    daejangtoon.updateProfile(profileUrl);
    daejangtoon.addImages(daejangtoonImages);
    daejangtoonRepository.save(daejangtoon);
  }

  /**
   * 대장툰 목록 조회 - 대장툰 메인
   *
   * @return List Daejangtoon
   */
  public List<Daejangtoon> daejangtoons() {
    return daejangtoonRepository.findAllByOrderByIdDesc();
  }

  /**
   * 대장툰 조회 - 회차별 조회
   *
   * @param daejangtoonId id
   * @return Daejangtoon
   */
  public Daejangtoon daejangtoon(Long daejangtoonId) {
    return daejangtoonRepository.findById(daejangtoonId)
        .orElseThrow(NotFoundToonException::new);
  }

  /**
   * 최신 대장툰 조회
   *
   * @return Daejangtoon
   */
  public Daejangtoon recent() {
    return daejangtoonRepository.findRecent();
  }

  /**
   * 대장툰 삭제
   *
   * @param daejangtoonId id
   */
  @Transactional
  public void remove(Long daejangtoonId) {
    Daejangtoon daejangtoon = daejangtoon(daejangtoonId);
    String profile = daejangtoon.getProfile();
    List<String> imageKeys = daejangtoon.getToonImages().stream()
        .map(DaejangtoonImage::getKey).toList();
    s3Manager.deleteImages(imageKeys);
    s3Manager.deleteProfile(profile);
    daejangtoonRepository.delete(daejangtoon);
  }

  /*--------------Private----------------------------Private----------------------------Private---*/

  /**
   * 이미지 저장
   *
   * @param images 회차 이미지 목록
   */
  private void saveImages(List<DaejangtoonImage> images) {
    daejangtoonImageRepository.saveAll(images);
  }
}
