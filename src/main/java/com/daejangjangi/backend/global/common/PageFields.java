package com.daejangjangi.backend.global.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageFields {

  @Schema(description = "현재 페이지 번호", example = "0")
  private int pageNumber;
  @Schema(description = "한 페이지에 표시되는 항목의 수", example = "10")
  private int pageSize;
  @Schema(description = "전체 데이터의 개수", example = "0")
  private long totalElements;
  @Schema(description = "전체 페이지 수", example = "0")
  private int totalPages;

  @Builder
  public PageFields(
      int pageNumber,
      int pageSize,
      long totalElements,
      int totalPages
  ) {
    this.pageNumber = pageNumber;
    this.pageSize = pageSize;
    this.totalElements = totalElements;
    this.totalPages = totalPages;
  }
}
