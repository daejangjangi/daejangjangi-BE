package com.daejangjangi.backend.faq.service;

import com.daejangjangi.backend.faq.exception.InvalidFaqCategoryException;
import com.daejangjangi.backend.qna.enums.QnaCategory;
import java.util.Arrays;
import java.util.Objects;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class FaqValidator {

  public void checkFaqCategory(String faqCategory) {
    if (!StringUtils.hasText(faqCategory) || Arrays.stream(QnaCategory.values())
        .noneMatch(type -> Objects.equals(type.name(), faqCategory))) {
      throw new InvalidFaqCategoryException();
    }
  }
}
