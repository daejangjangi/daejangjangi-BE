package com.daejangjangi.backend.post.repository;

import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.post.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface PostRepository extends JpaRepository<Post, Long> {

  Page<Post> findByMember(Member member, Pageable pageable);

  Page<Post> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword,
      Pageable pageable);

  @Modifying(clearAutomatically = true)
  @Query("UPDATE Post p SET p.hit = p.hit + 1 WHERE p = :post")
  void updateHit(@Param(("post")) Post post);

  @Modifying
  @Query("UPDATE Post p SET p.likeCount = p.likeCount - 1 WHERE p = :post AND p.likeCount > 0")
  void decreaseLikes(@Param("post") Post post);

  Page<Post> findByLikeCountGreaterThanEqual(Long likeCount, Pageable pageable);

}