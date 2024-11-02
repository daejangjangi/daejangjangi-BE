package com.daejangjangi.backend.cardnews.service;

import com.daejangjangi.backend.cardnews.domain.entity.Cardnews;
import com.daejangjangi.backend.cardnews.domain.entity.CardnewsImage;
import com.daejangjangi.backend.cardnews.domain.mapper.CardnewsImageMapper;
import com.daejangjangi.backend.cardnews.exception.NotFoundCardnewsException;
import com.daejangjangi.backend.cardnews.repository.CardnewsRepository;
import com.daejangjangi.backend.file.domain.ImageInfoDto;
import com.daejangjangi.backend.file.service.FileValidator;
import com.daejangjangi.backend.file.service.S3Manager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CardnewsService {

  private final String CARDNEWS_ROOT_DIRECTORY = "카드뉴스/";

  private final CardnewsRepository cardnewsRepository;
  private final FileValidator fileValidator;
  private final S3Manager s3Manager;

  /**
   * 카드뉴스 등록
   *
   * @param cardnews     카드뉴스
   * @param profileImage 프로픨 이미지
   * @param newsImages   뉴스 이미지 목록
   */
  public void save(Cardnews cardnews, MultipartFile profileImage, List<MultipartFile> newsImages) {
    fileValidator.validate(profileImage);
    fileValidator.validate(newsImages);
    String profileUrl = s3Manager.upload(profileImage);
    List<ImageInfoDto> images = s3Manager.upload(CARDNEWS_ROOT_DIRECTORY, newsImages);
    List<CardnewsImage> cardnewsImages = CardnewsImageMapper.INSTANCE.dtoToEntity(images);
    cardnews.addImages(cardnewsImages);
    cardnews.updateProfile(profileUrl);
    cardnewsRepository.save(cardnews);
  }

  /**
   * 카드 뉴스 삭제
   *
   * @param cardnews 카드 뉴스
   */
  public void remove(Cardnews cardnews) {
    String profile = cardnews.getProfile();
    List<String> imageKeys = cardnews.getNewsImages().stream().map(CardnewsImage::getKey).toList();
    s3Manager.deleteImages(imageKeys);
    s3Manager.deleteProfile(profile);
    cardnewsRepository.delete(cardnews);
  }

  /**
   * 카드뉴스 조회 by id
   *
   * @param cardnewsId 카드뉴스 id
   * @return Cardnews
   */
  public Cardnews findById(Long cardnewsId) {
    return cardnewsRepository.findById(cardnewsId).orElseThrow(NotFoundCardnewsException::new);
  }
}
