package com.daejangjangi.backend.daejangtoon.service;

import com.daejangjangi.backend.daejangtoon.domain.entity.Daejangtoon;
import com.daejangjangi.backend.daejangtoon.domain.entity.DaejangtoonChapter;
import com.daejangjangi.backend.daejangtoon.domain.entity.DaejangtoonImage;
import com.daejangjangi.backend.daejangtoon.domain.mapper.DaejangtoonImageMapper;
import com.daejangjangi.backend.daejangtoon.exception.NotFoundChapterException;
import com.daejangjangi.backend.daejangtoon.repository.DaejangtoonChapterRepository;
import com.daejangjangi.backend.file.domain.ImageInfoDto;
import com.daejangjangi.backend.file.service.FileValidator;
import com.daejangjangi.backend.file.service.S3Manager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class DaejangtoonChapterService {

  private final String DAEJANGTOON_ROOT_DIRECTORY = "대장툰/";

  private final DaejangtoonChapterRepository daejangtoonChapterRepository;
  private final FileValidator fileValidator;
  private final S3Manager s3Manager;

  /**
   * 대장툰 회차 등록
   *
   * @param daejangtoon  대장툰
   * @param chapter      회차
   * @param profileImage 프로필 이미지
   * @param toonImages   컷 이미지 목록
   */
  @Transactional
  public void save(
      Daejangtoon daejangtoon,
      DaejangtoonChapter chapter,
      MultipartFile profileImage,
      List<MultipartFile> toonImages
  ) {
    fileValidator.validateImage(profileImage);
    fileValidator.validateImages(toonImages);
    String profileUrl = s3Manager.upload(profileImage);
    List<ImageInfoDto> images =
        s3Manager.upload(DAEJANGTOON_ROOT_DIRECTORY + chapter.getChapter() + "/", toonImages);
    List<DaejangtoonImage> daejangtoonImages = DaejangtoonImageMapper.INSTANCE.dtoToEntity(images);
    chapter.updateProfile(profileUrl);
    chapter.addImages(daejangtoonImages);
    daejangtoon.addChapter(chapter);
    daejangtoonChapterRepository.save(chapter);
  }

  /**
   * 회차 조회 by 대장툰, chapter
   *
   * @param daejangtoon 대장툰
   * @param chapter     회차
   * @return DaejangtoonChapter
   */
  public DaejangtoonChapter getChapter(Daejangtoon daejangtoon, Integer chapter) {
    return daejangtoonChapterRepository.findByDaejangtoonAndChapter(daejangtoon, chapter)
        .orElseThrow(NotFoundChapterException::new);
  }

  /**
   * 회차 조회 by 대장툰, chapter with hit
   *
   * @param daejangtoon 대장툰
   * @param chapter     회차
   * @return DaejangtoonChapter
   */
  @Transactional
  public DaejangtoonChapter getChapterWithHit(Daejangtoon daejangtoon, Integer chapter) {
    // 중복 조회 불가능하도록 처리하기 - 레디스 캐싱 이용
    DaejangtoonChapter daejangtoonChapter = getChapter(daejangtoon, chapter);
    daejangtoonChapterRepository.updateHit(daejangtoonChapter.getId());
    return daejangtoonChapter;
  }

  /**
   * 최신 등록 회차 조회
   *
   * @return Daejangtoon
   */
  public DaejangtoonChapter recent(Long daejangtoonId) {
    return daejangtoonChapterRepository.findRecent(daejangtoonId);
  }

  /**
   * 대장툰 회차 삭제
   *
   * @param chapter 회차
   */
  @Transactional
  public void remove(DaejangtoonChapter chapter) {
    String profile = chapter.getProfile();
    List<String> imageKeys = chapter.getToonImages().stream()
        .map(DaejangtoonImage::getKey).toList();
    s3Manager.deleteImages(imageKeys);
    s3Manager.deleteProfile(profile);
    daejangtoonChapterRepository.delete(chapter);
  }
}
