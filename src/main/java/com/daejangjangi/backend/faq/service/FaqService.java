package com.daejangjangi.backend.faq.service;

import com.daejangjangi.backend.faq.domain.entity.Faq;
import com.daejangjangi.backend.faq.repository.FaqRepository;
import com.daejangjangi.backend.member.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FaqService {

  private final FaqRepository faqRepository;

  /**
   * 질문 저장
   *
   * @param member 로그인 회원
   * @param faq    질문
   */
  public void save(Member member, Faq faq) {
    faq.updateParent(member);
    faqRepository.save(faq);
  }
}
