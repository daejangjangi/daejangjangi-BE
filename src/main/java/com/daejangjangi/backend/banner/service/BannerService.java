package com.daejangjangi.backend.banner.service;

import com.daejangjangi.backend.banner.domain.dto.BannerResponseDto.BannerInfo;
import com.daejangjangi.backend.banner.domain.entity.Banner;
import com.daejangjangi.backend.banner.domain.mapper.BannerMapper;
import com.daejangjangi.backend.banner.exception.AlreadyExistsBannerException;
import com.daejangjangi.backend.banner.repository.BannerRepository;
import com.daejangjangi.backend.file.service.FileValidator;
import com.daejangjangi.backend.file.service.S3Manager;
import com.daejangjangi.backend.product.domain.entity.Product;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class BannerService {

  private final BannerRepository bannerRepository;
  private final FileValidator fileValidator;
  private final S3Manager s3Manager;

  public void save(MultipartFile bannerImage, Product product) {
    if (bannerRepository.existsByProduct(product)) {
      throw new AlreadyExistsBannerException();
    }
    fileValidator.validateImage(bannerImage);
    String bannerImageLink = s3Manager.upload(bannerImage);
    Banner banner = Banner.builder()
        .image(bannerImageLink)
        .product(product)
        .build();
    bannerRepository.save(banner);
  }

  public List<BannerInfo> list() {
    List<Banner> bannerList = bannerRepository.findAll();
    return BannerMapper.INSTANCE.entityToResponse(bannerList);
  }
}
