package com.daejangjangi.backend.post.repository;

import com.daejangjangi.backend.post.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostLockRepository extends JpaRepository<Post, Long> {

  @Query(value = "select get_lock(:key, 3000)", nativeQuery = true)
  void getLock(@Param("key") String key);

  @Query(value = "select release_lock(:key)", nativeQuery = true)
  void releaseLock(@Param("key") String key);

}
