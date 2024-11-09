package com.daejangjangi.backend.newsletter.service;

import com.daejangjangi.backend.category.domain.Category;
import com.daejangjangi.backend.file.service.FileValidator;
import com.daejangjangi.backend.file.service.S3Manager;
import com.daejangjangi.backend.newsletter.domain.dto.NewsletterResponseDto.NewsletterInfo;
import com.daejangjangi.backend.newsletter.domain.entity.Newsletter;
import com.daejangjangi.backend.newsletter.domain.mapper.NewsletterMapper;
import com.daejangjangi.backend.newsletter.exception.NotFoundNewsletterException;
import com.daejangjangi.backend.newsletter.repository.NewsletterRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class NewsletterService {

  private final NewsletterRepository newsletterRepository;
  private final FileValidator fileValidator;
  private final S3Manager s3Manager;

  /**
   * 뉴스레터 저장
   *
   * @param newsletter   뉴스레터
   * @param profileImage 프로픨 이미지
   * @param category     뉴스레터 카테고리
   */
  public void save(
      Newsletter newsletter,
      MultipartFile profileImage,
      Category category
  ) {
    fileValidator.validateImage(profileImage);
    String profileUrl = s3Manager.upload(profileImage);
    newsletter.updateProfile(profileUrl);
    newsletter.updateCategory(category);
    newsletterRepository.save(newsletter);
  }

  /**
   * 뉴스레터 조회 by ID
   *
   * @param newsletterId 뉴스레터 ID
   * @return Newsletter
   */
  public Newsletter findById(Long newsletterId) {
    return newsletterRepository.findById(newsletterId)
        .orElseThrow(NotFoundNewsletterException::new);
  }

  /**
   * 뉴스레터 삭제
   *
   * @param newsletter 뉴스레터
   */
  public void remove(Newsletter newsletter) {
    s3Manager.deleteProfile(newsletter.getProfileImage());
    newsletterRepository.delete(newsletter);
  }

  /**
   * 뉴스레터 목록 조회
   *
   * @return List NewsletterInfo
   */
  public List<NewsletterInfo> findAll() {
    List<Newsletter> newsletters = newsletterRepository.findAll();
    return NewsletterMapper.INSTANCE.newslettersToInfoList(newsletters);
  }
}
