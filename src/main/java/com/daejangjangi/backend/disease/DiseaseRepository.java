package com.daejangjangi.backend.disease;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiseaseRepository extends JpaRepository<Disease, Long> {

  List<Disease> findByNameIn(List<String> names);
}
