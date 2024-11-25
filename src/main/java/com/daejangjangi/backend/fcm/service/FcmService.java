package com.daejangjangi.backend.fcm.service;

import com.daejangjangi.backend.fcm.domain.entity.FcmToken;
import com.daejangjangi.backend.fcm.repository.FcmTokenRepository;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.member.exception.NotFoundMemberException;
import com.daejangjangi.backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FcmService {

  private final FcmTokenRepository fcmTokenRepository;
  private final MemberRepository memberRepository;

  /**
   * FCM 토큰 저장
   *
   * @param member   회원 정보
   * @param fcmToken FCM 토큰
   */
  @Transactional
  public void save(Member member, String fcmToken) {
    fcmTokenRepository.findByMemberAndFcmToken(member, fcmToken)
        .map(token -> {
          token.updateLastUsedAt();
          return token;
        })
        .orElseGet(() ->
            fcmTokenRepository.save(FcmToken.builder()
                .fcmToken(fcmToken)
                .member(member)
                .build()));
  }

  /**
   * FCM 토큰 삭제
   *
   * @param token FCM 토큰
   */
  @Transactional
  public void delete(String token) {
    fcmTokenRepository.deleteByFcmToken(token);
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
