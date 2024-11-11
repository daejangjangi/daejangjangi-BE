package com.daejangjangi.backend.file.service;

import com.daejangjangi.backend.file.exception.EmptyFileException;
import com.daejangjangi.backend.file.exception.NotAccessibleContentTypeException;
import com.daejangjangi.backend.file.exception.NotImageFileException;
import com.daejangjangi.backend.file.exception.NotVideoFileException;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileValidator {

  private static final List<String> allowableContentType = Arrays.asList(
      "image/png", "video/mp4"
  );

  public void validateImages(List<MultipartFile> files) {
    files.forEach(this::validateImage);
  }

  public void validateImage(MultipartFile file) {
    checkIsEmpty(file);
    String contentType = file.getContentType();
    if (StringUtils.hasText(contentType)
        && !contentType.startsWith("image/")) {
      throw new NotImageFileException();
    } else if (!allowableContentType.contains(contentType)) {
      throw new NotAccessibleContentTypeException();
    }
  }

  public void validateVideo(MultipartFile file) {
    checkIsEmpty(file);
    String contentType = file.getContentType();
    if (StringUtils.hasText(contentType)
        && !contentType.startsWith("video/")) {
      throw new NotVideoFileException();
    } else if (!allowableContentType.contains(contentType)) {
      throw new NotAccessibleContentTypeException();
    }
  }

  private void checkIsEmpty(MultipartFile file) {
    if (file.isEmpty()) {
      throw new EmptyFileException();
    }
  }
}
