package com.daejangjangi.backend.cardnews.repository;

import com.daejangjangi.backend.cardnews.domain.entity.Cardnews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CardnewsRepository extends JpaRepository<Cardnews, Long> {

  @Query("SELECT c FROM Cardnews c ORDER BY c.createdAt DESC LIMIT 1")
  Cardnews findRecent();
}
