package com.daejangjangi.backend.qna.repository;

import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.qna.domain.entity.Qna;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QnaRepository extends JpaRepository<Qna, Long> {

  List<Qna> findByMember(Member member);
}
