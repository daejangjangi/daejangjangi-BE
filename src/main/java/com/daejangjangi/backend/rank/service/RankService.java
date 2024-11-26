package com.daejangjangi.backend.rank.service;

import jakarta.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RankService {

  private static final Long TOP_N = 3L;

  private final RedisTemplate<String, String> redisTemplate;

  @Value("${custom.redis.bind.ranking-key}")
  private String searchRankingKey;

  @Value("${custom.redis.bind.user-keyword}")
  private String searchUserKeywordPrefix;

  private ZSetOperations<String, String> zSetOperations;

  @PostConstruct
  private void init() {
    zSetOperations = redisTemplate.opsForZSet();
  }

  /**
   * 인기 검색어 조회
   *
   * @return Set - String
   */
  public Set<String> getSearchRankingTop() {
    Set<String> rankingSet = zSetOperations.reverseRange(searchRankingKey, 0, TOP_N - 1);
    if (rankingSet == null || rankingSet.size() < TOP_N) {
      rankingSet = new HashSet<>();
      rankingSet.add("변비");
      rankingSet.add("과민성");
      rankingSet.add("식이섬유");
    }
    return rankingSet;
  }

  /**
   * 검색어 중 필요없는 내용 제거 - 24시간 마다 제거
   */
  public void cleanupLowRankingKeywords() {
    log.info("scheduler start!");
    try {
      Long totalKeywordsSize = zSetOperations.size(searchRankingKey);
      if (totalKeywordsSize == null || totalKeywordsSize <= TOP_N) {
        return;
      }
      Set<String> keywordsToRemove = zSetOperations.reverseRange(searchRankingKey, TOP_N, -1);
      zSetOperations.removeRange(searchRankingKey, 0, totalKeywordsSize - (TOP_N + 1));
      cleanupUserSearchHistory(keywordsToRemove);
    } catch (Exception e) {
      log.error("Exception : ", e);
      throw new RuntimeException("검색 키워드 일괄 삭제 실패"); // TODO : 예외 추가하기
    }
  }

  /**
   * 회원 검색어 기록 삭제
   *
   * @param keywordsToRemove 삭제할 검색어 목록
   */
  private void cleanupUserSearchHistory(Set<String> keywordsToRemove) {
    Set<String> userKeys = redisTemplate.keys(searchUserKeywordPrefix + "*");
    if (userKeys == null || userKeys.isEmpty()) {
      return;
    }

    for (String userKey : userKeys) {
      String keyword = userKey.substring(userKey.lastIndexOf(":") + 1);
      if (keywordsToRemove.contains(keyword)) {
        redisTemplate.delete(userKey);
      }
    }
  }
}
