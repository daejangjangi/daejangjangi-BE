package com.daejangjangi.backend.faq.repository;

import com.daejangjangi.backend.faq.domain.entity.Faq;
import com.daejangjangi.backend.qna.Qna;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaqRepository extends JpaRepository<Faq, Long> {

  boolean existsByQna(Qna qna);
}
