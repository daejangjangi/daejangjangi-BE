package com.daejangjangi.backend.stoolanalysis.repository;

import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.stoolanalysis.domain.entity.StoolDiagnosis;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoolDiagnosisRepository extends JpaRepository<StoolDiagnosis, Long> {

  Page<StoolDiagnosis> findByMemberAndResultSavedAndStoolAtGreaterThanEqual(Member member,
      boolean resultSaved, LocalDateTime date, Pageable pageable);
}
