package com.daejangjangi.backend.comment.repository;

import com.daejangjangi.backend.comment.domain.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {

}
