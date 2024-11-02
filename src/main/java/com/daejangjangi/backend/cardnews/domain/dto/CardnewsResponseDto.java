package com.daejangjangi.backend.cardnews.domain.dto;

import java.time.LocalDateTime;
import java.util.List;

public class CardnewsResponseDto {

  public record Cardnews(
      List<CardnewsItem> cardnewsItems
  ) {

  }

  public record CardnewsItem(
      Long id,
      String profile,
      String title
  ) {

  }

  public record Info(
      String title,
      LocalDateTime createdAt,
      List<String> newsImages
  ) {

  }
}
