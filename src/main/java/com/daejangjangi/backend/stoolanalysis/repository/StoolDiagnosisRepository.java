package com.daejangjangi.backend.stoolanalysis.repository;

import com.daejangjangi.backend.stoolanalysis.domain.entity.StoolDiagnosis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoolDiagnosisRepository extends JpaRepository<StoolDiagnosis, Long> {

}
