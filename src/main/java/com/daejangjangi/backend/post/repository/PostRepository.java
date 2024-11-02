package com.daejangjangi.backend.post.repository;

import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.post.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post, Long> {

  Page<Post> findByMember(Member member, Pageable pageable);

  Page<Post> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword,
      Pageable pageable);

}