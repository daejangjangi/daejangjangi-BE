package com.daejangjangi.backend.file.service;

import com.daejangjangi.backend.file.exception.EmptyFileException;
import com.daejangjangi.backend.file.exception.NotImageFileException;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileValidator {

  public void validate(List<MultipartFile> files) {
    files.forEach(this::validate);
  }

  public void validate(MultipartFile file) {
    checkIsEmpty(file);
    checkImageType(file);
  }

  private void checkIsEmpty(MultipartFile file) {
    if (file.isEmpty()) {
      throw new EmptyFileException();
    }
  }

  private void checkImageType(MultipartFile file) {
    String contentType = file.getContentType();
    if (StringUtils.hasText(contentType) && !contentType.startsWith("image/")) {
      throw new NotImageFileException();
    }
  }
}
