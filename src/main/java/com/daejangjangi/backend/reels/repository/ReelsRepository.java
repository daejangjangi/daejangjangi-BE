package com.daejangjangi.backend.reels.repository;

import com.daejangjangi.backend.reels.domain.entity.Reels;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReelsRepository extends JpaRepository<Reels, Long> {

  @Query("SELECT r FROM Reels r ORDER BY r.id DESC")
  List<Reels> findAllOrderByIdDesc();
}
