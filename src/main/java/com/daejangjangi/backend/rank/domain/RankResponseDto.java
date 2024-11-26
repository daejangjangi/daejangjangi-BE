package com.daejangjangi.backend.rank.domain;

import java.util.Set;

public class RankResponseDto {

  public record SearchRankingTop(

      Set<String> top3
  ) {

  }
}
