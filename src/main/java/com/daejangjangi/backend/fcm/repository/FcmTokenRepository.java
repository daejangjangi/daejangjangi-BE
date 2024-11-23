package com.daejangjangi.backend.fcm.repository;

import com.daejangjangi.backend.fcm.domain.entity.FcmToken;
import com.daejangjangi.backend.member.domain.entity.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {

  Optional<FcmToken> findByMemberEmailAndFcmToken(String email, String token);

  void deleteByFcmToken(String fcmToken);

  void deleteByMember(Member member);

  List<FcmToken> findByMember(Member member);
}
