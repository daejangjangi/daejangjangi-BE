package com.daejangjangi.backend.product.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@SuppressWarnings("NonAsciiCharacters")
public enum ProductGroupEnum {
  유산균("유산균"),
  저포드맵("저포드맵"),
  식이섬유("식이섬유"),
  간식("간식"),
  생활용품_리빙("생활용품.리빙");

  private final String value;

  public static ProductGroupEnum findByValue(String value) {
    for (ProductGroupEnum productGroupEnum : ProductGroupEnum.values()) {
      if (productGroupEnum.getValue().equals(value)) {
        return productGroupEnum;
      }
    }
    return null;
  }
}
