package com.daejangjangi.backend.faq.repository;

import com.daejangjangi.backend.faq.domain.entity.Faq;
import com.daejangjangi.backend.qna.domain.entity.Qna;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FaqRepository extends JpaRepository<Faq, Long> {

  @Query("SELECT f FROM Faq f JOIN FETCH f.qna")
  List<Faq> findAllWithQna();

  boolean existsByQna(Qna qna);
}
