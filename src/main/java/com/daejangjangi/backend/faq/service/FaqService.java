package com.daejangjangi.backend.faq.service;

import com.daejangjangi.backend.faq.domain.dto.FaqResponseDto;
import com.daejangjangi.backend.faq.domain.entity.Faq;
import com.daejangjangi.backend.faq.domain.mapper.FaqMapper;
import com.daejangjangi.backend.faq.exception.NotFoundFaqException;
import com.daejangjangi.backend.faq.exception.QnaDuplicationException;
import com.daejangjangi.backend.faq.repository.FaqRepository;
import com.daejangjangi.backend.qna.Qna;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FaqService {

  private final FaqRepository faqRepository;

  /**
   * 자주 묻는 질문 등록
   *
   * @param qna 질문
   */
  @Transactional
  public void save(Qna qna) {
    checkQna(qna);
    Faq faq = Faq.builder()
        .qna(qna)
        .build();
    faqRepository.save(faq);
  }

  /**
   * 전체 질문 조회
   *
   * @return List<FaqResponseDto.FaqDto>
   */
  public List<FaqResponseDto.FaqDto> findAll() {
    List<Faq> faqList = faqRepository.findAll();
    return FaqMapper.INSTANCE.entityToFaqsResponse(faqList);
  }

  /**
   * FAQ 조회 by id
   *
   * @param id FAQ 아이디
   * @return FAQ
   */
  public Faq findById(Long id) {
    return faqRepository.findById(id).orElseThrow(NotFoundFaqException::new);
  }

  /**
   * FAQ 삭제
   *
   * @param faq FAQ
   */
  @Transactional
  public void remove(Faq faq) {
    faqRepository.delete(faq);
  }

  /*--------------Private----------------------------Private----------------------------Private---*/

  /**
   * QnA 중복 여부 확인
   *
   * @param qna 질문
   */
  private void checkQna(Qna qna) {
    if (faqRepository.existsByQna(qna)) {
      throw new QnaDuplicationException();
    }
  }
}
