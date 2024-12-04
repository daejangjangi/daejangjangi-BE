package com.daejangjangi.backend.product.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductLookUpLogDto {

  private Long productId;
  private Long count;
}
