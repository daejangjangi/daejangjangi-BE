package com.daejangjangi.backend.qna.domain.entity;

import com.daejangjangi.backend.global.common.BaseEntity;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.qna.domain.enums.QnaCategory;
import com.daejangjangi.backend.qna.domain.enums.QnaStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "qnas")
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Qna extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "qna_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  @Column(name = "qna_category", nullable = false)
  @Enumerated(EnumType.STRING)
  private QnaCategory category;

  @Column(name = "qna_status", nullable = false)
  @Enumerated(EnumType.STRING)
  private QnaStatus status;

  @Column(name = "qna_question", length = 500, nullable = false)
  private String question;

  @Column(name = "qna_answer", length = 500)
  private String answer;

  @Builder
  public Qna(
      QnaCategory category,
      String question
  ) {
    this.category = category;
    this.question = question;

    this.status = QnaStatus.답변대기;
  }

  /*-------------Business Logic---------------------------Business Logic--------------------------*/

  /**
   * 회원 갱신
   *
   * @param member 회원
   */
  public void updateParent(Member member) {
    this.member = member;
  }

  /**
   * 답변하기
   *
   * @param answer faq 답변
   */
  public void updateAnswer(String answer) {
    this.answer = answer;
  }

  /**
   * 상태 변경
   */
  public void updateStatus() {
    this.status = QnaStatus.답변완료;
  }
}
