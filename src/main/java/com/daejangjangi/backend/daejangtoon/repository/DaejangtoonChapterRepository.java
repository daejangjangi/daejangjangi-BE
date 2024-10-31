package com.daejangjangi.backend.daejangtoon.repository;

import com.daejangjangi.backend.daejangtoon.domain.entity.Daejangtoon;
import com.daejangjangi.backend.daejangtoon.domain.entity.DaejangtoonChapter;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DaejangtoonChapterRepository extends JpaRepository<DaejangtoonChapter, Long> {

  Optional<DaejangtoonChapter> findByDaejangtoonAndChapter(Daejangtoon daejangtoon,
      Integer chapter);

  @Query("SELECT dc FROM DaejangtoonChapter dc WHERE dc.daejangtoon.id = :id ORDER BY dc.createdAt DESC LIMIT 1")
  DaejangtoonChapter findRecent(Long id);
}
