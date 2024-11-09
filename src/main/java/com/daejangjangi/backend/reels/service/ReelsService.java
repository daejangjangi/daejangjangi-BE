package com.daejangjangi.backend.reels.service;

import com.daejangjangi.backend.file.service.FileValidator;
import com.daejangjangi.backend.file.service.S3Manager;
import com.daejangjangi.backend.reels.domain.entity.Reels;
import com.daejangjangi.backend.reels.exception.NotFoundReelsException;
import com.daejangjangi.backend.reels.repository.ReelsRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ReelsService {

  private final ReelsRepository reelsRepository;
  private final FileValidator fileValidator;
  private final S3Manager s3Manager;

  /**
   * 릴스 영상 등록
   *
   * @param reels      릴스 영상
   * @param reelsVideo 릴스 영상 파일
   */
  public void register(Reels reels, MultipartFile reelsVideo) {
    fileValidator.validateVideo(reelsVideo);
    String videoUrl = s3Manager.upload(reelsVideo);
    reels.updateVideo(videoUrl);
    reelsRepository.save(reels);
  }

  /**
   * 릴스 조회  by ID
   *
   * @param reelsId 릴스 ID
   * @return Reels
   */
  public Reels findById(Long reelsId) {
    return reelsRepository.findById(reelsId).orElseThrow(NotFoundReelsException::new);
  }

  /**
   * 릴스 영상 삭제
   *
   * @param reels 릴스 영상
   */
  public void remove(Reels reels) {
    s3Manager.deleteProfile(reels.getReelsVideo());
    reelsRepository.delete(reels);
  }

  /**
   * 릴스 영상 목록 조회
   *
   * @return List Reels
   */
  public List<Reels> findAll() {
    return reelsRepository.findAllOrderByIdDesc();
  }
}
