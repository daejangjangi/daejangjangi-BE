package com.daejangjangi.backend.global.config;

import com.daejangjangi.backend.rank.service.RankService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerConfig {

  private final RankService rankService;

  /**
   * 매일 00:00 마다 수행
   */
  @Scheduled(cron = "0 0 0 * * *")
  public void cleanupSearchRankings() {
    rankService.cleanupLowRankingKeywords();
  }
}
