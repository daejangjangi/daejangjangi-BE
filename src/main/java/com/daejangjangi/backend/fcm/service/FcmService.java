package com.daejangjangi.backend.fcm.service;

import com.daejangjangi.backend.fcm.domain.entity.FcmToken;
import com.daejangjangi.backend.fcm.repository.FcmTokenRepository;
import com.daejangjangi.backend.member.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FcmService {

  private final FcmTokenRepository fcmTokenRepository;

  /**
   * Fcm 토큰 저장
   *
   * @param fcmToken 토큰 정보
   * @return FcmToken
   */
  // TODO 로그인 시 fcm 토큰 저장
  @Transactional
  public FcmToken save(FcmToken fcmToken) {
    return fcmTokenRepository.findByMemberAndToken(fcmToken.getMember(), fcmToken.getToken())
        .map(token -> {
          token.updateLastUsedAt();
          return token;
        })
        .orElse(fcmTokenRepository.save(fcmToken));
  }

  /**
   * Fcm 토큰 삭제
   *
   * @param token fcm 토큰
   */
  // TODO 로그아웃 시 fcm 토큰 삭제
  @Transactional
  public void delete(String token) {
    fcmTokenRepository.deleteByToken(token);
  }

  /**
   * 회원 정보를 가진 Fcm 토큰 삭제
   *
   * @param member 회원 정보
   */
  @Transactional
  public void deleteByMember(Member member) {
    fcmTokenRepository.deleteByMember(member);
  }
}
