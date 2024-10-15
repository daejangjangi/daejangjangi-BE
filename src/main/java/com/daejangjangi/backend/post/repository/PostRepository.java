package com.daejangjangi.backend.post.repository;

import com.daejangjangi.backend.post.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}