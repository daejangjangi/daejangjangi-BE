package com.daejangjangi.backend.stoollog.repository;

import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.stoollog.domain.entity.Stoollog;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoollogRepository extends JpaRepository<Stoollog, Long> {

  List<Stoollog> findByMemberAndLoggedAtBetweenOrderByLoggedAtAsc(Member member,
      LocalDateTime start, LocalDateTime end);

}
