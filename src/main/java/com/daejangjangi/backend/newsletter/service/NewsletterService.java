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

  public Newsletter findById(Long newsletterId) {
    return newsletterRepository.findById(newsletterId)
        .orElseThrow(NotFoundNewsletterException::new);
  }

  public void remove(Newsletter newsletter) {
    newsletterRepository.delete(newsletter);
  }

  public List<NewsletterInfo> findAll() {
    List<Newsletter> newsletters = newsletterRepository.findAll();
    return NewsletterMapper.INSTANCE.newslettersToInfoList(newsletters);
  }
}
