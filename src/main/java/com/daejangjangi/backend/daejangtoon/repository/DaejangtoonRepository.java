package com.daejangjangi.backend.daejangtoon.repository;

import com.daejangjangi.backend.daejangtoon.domain.entity.Daejangtoon;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DaejangtoonRepository extends JpaRepository<Daejangtoon, Long> {

  List<Daejangtoon> findAllByOrderByIdDesc();

  Optional<Daejangtoon> findByChapter(Integer chapter);

  @Query("SELECT d FROM Daejangtoon d ORDER BY d.createdAt DESC LIMIT 1")
  Daejangtoon findRecent();
}
