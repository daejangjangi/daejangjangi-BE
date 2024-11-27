package com.daejangjangi.backend.rank.controller;

import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.rank.domain.RankResponseDto.SearchRankingTop;
import com.daejangjangi.backend.rank.service.RankService;
import java.util.Set;
import org.apache.commons.lang3.ObjectUtils.Null;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ranks")
public class RankController implements RankApi {

  private final RankService rankService;

  public RankController(RankService rankService) {
    this.rankService = rankService;
  }

  @PreAuthorize("hasAuthority('MEMBER')")
  @GetMapping("/search/top")
  public ApiGlobalResponse<SearchRankingTop> searchRankingTop() {
    Set<String> top = rankService.getSearchRankingTop();
    SearchRankingTop response = new SearchRankingTop(top);
    return ApiGlobalResponse.ok(response);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping
  public ApiGlobalResponse<Null> deleteLowRanking() {
    rankService.cleanupLowRankingKeywords();
    return ApiGlobalResponse.ok();
  }
}
