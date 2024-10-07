package com.daejangjangi.backend.member;

import com.daejangjangi.backend.member.exception.EmailDuplicationException;
import com.daejangjangi.backend.member.exception.NicknameDuplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  public void checkEmail(String email) {
    if (memberRepository.existsByEmail(email)) {
      throw new EmailDuplicationException();
    }
  }

  public void checkNickname(String nickname) {
    if (memberRepository.existsByNickname(nickname)) {
      throw new NicknameDuplicationException();
    }
  }
}
