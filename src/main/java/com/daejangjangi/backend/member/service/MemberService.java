package com.daejangjangi.backend.member.service;

import com.daejangjangi.backend.member.exception.EmailDuplicationException;
import com.daejangjangi.backend.member.exception.NicknameDuplicationException;
import com.daejangjangi.backend.member.repository.MemberRepository;
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
