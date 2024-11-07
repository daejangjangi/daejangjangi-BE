package com.daejangjangi.backend.qna.service;

import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.qna.domain.entity.Qna;
import com.daejangjangi.backend.qna.domain.mapper.QnaMapper;
import com.daejangjangi.backend.qna.domain.dto.QnaResponseDto;
import com.daejangjangi.backend.qna.exception.NotFoundQnaException;
import com.daejangjangi.backend.qna.repository.QnaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QnaService {

  private final QnaRepository qnaRepository;

  /**
   * QnA 등록
   *
   * @param member 로그인 회원
   * @param qna    QnA
   */
  @Transactional
  public void save(Member member, Qna qna) {
    qna.updateParent(member);
    qnaRepository.save(qna);
  }

  /**
   * 로그인 회원 질문 목록 조회
   *
   * @param member 회원
   * @return List<QnaResponseDto.MyInfo>
   */
  public List<QnaResponseDto.MyInfo> findMyQnaList(Member member) {
    List<Qna> myQnaList = qnaRepository.findByMember(member);
    return QnaMapper.INSTANCE.entityToMyQnaListResponse(myQnaList);
  }

  /**
   * 답변 등록
   *
   * @param qna    QnA
   * @param answer QnA 답변
   */
  @Transactional
  public void registerAnswer(Qna qna, String answer) {
    qna.updateAnswer(answer);
    qna.updateStatus();
  }

  /**
   * QnA 조회 by id
   *
   * @param id QnA id
   * @return QnA
   */
  public Qna findById(Long id) {
    return qnaRepository.findById(id).orElseThrow(NotFoundQnaException::new);
  }

  /**
   * QnA 삭제
   *
   * @param qna QnA
   */
  public void remove(Qna qna) {
    qnaRepository.delete(qna);
  }
}
