package com.daejangjangi.backend.file.service;

import com.daejangjangi.backend.file.domain.ImageInfoDto;
import com.daejangjangi.backend.file.util.OrderConverter;
import com.daejangjangi.backend.global.exception.ServerDataException;
import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
@Slf4j
@RequiredArgsConstructor
public class S3Manager {

  private final S3Template s3Template;

  @Value("${spring.cloud.aws.s3.bucket}")
  private String bucketName;
  @Value("${spring.cloud.aws.region.static}")
  private String region;

  public String upload(MultipartFile file) {
    try {
      String fileName = file.getOriginalFilename();
      String extension = StringUtils.getFilenameExtension(fileName);
      String key = UUID.randomUUID() + "." + extension;
      S3Resource s3Resource = s3Template.upload(bucketName, key, file.getInputStream(),
          ObjectMetadata.builder().contentType(file.getContentType()).build());
      return s3Resource.getURL().toString();
    } catch (IOException e) {
      log.error("File-UploadError : ", e);
      throw new ServerDataException();
    }
  }

  public List<ImageInfoDto> upload(String directory, List<MultipartFile> files) {
    List<ImageInfoDto> imageUrls = new ArrayList<>();
    List<String> uploadedKeys = new ArrayList<>();
    try {
      for (MultipartFile file : files) {
        String originFileName = file.getOriginalFilename();
        String fileName = UUID.randomUUID() + "_" + originFileName;
        String key = directory + "/" + fileName;
        S3Resource s3Resource = s3Template.upload(bucketName, key, file.getInputStream(),
            ObjectMetadata.builder().contentType(file.getContentType()).build());
        uploadedKeys.add(key); // 업로드된 이미지 key 저장
        ImageInfoDto imageDto = ImageInfoDto.builder()
            .key(key)
            .order(OrderConverter.getOrder(originFileName))
            .image(s3Resource.getURL().toString())
            .build();
        imageUrls.add(imageDto);
      }
      return imageUrls;
    } catch (IOException e) {
      log.error("File-UploadError : ", e);
      deleteUploadedFiles(uploadedKeys); // 보상 트랜잭션 느낌. -> 업로드 실패 시 기존에 업로드되었던 이미지 삭제
      throw new ServerDataException();
    }
  }

  public void deleteProfile(String profile) {
    String s3Url = "https://" + bucketName + ".s3." + region + ".amazonaws.com/";
    try {
      String profileKey = profile.replace(s3Url, "");
      s3Template.deleteObject(bucketName, profileKey);
    } catch (Exception e) {
      log.error("Error deleting file:", e);
    }
  }

  public void deleteImages(List<String> imageKeys) {
    try {
      imageKeys.forEach(key -> s3Template.deleteObject(bucketName, key));
    } catch (Exception e) {
      log.error("Error deleting file:", e);
    }
  }

  /*--------------Private----------------------------Private----------------------------Private---*/

  private void deleteUploadedFiles(List<String> keys) {
    for (String key : keys) {
      try {
        s3Template.deleteObject(bucketName, key);
      } catch (Exception e) {
        log.error("Error deleting file: {}", key, e);
      }
    }
  }
}
