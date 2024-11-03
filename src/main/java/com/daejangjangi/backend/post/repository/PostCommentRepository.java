package com.daejangjangi.backend.post.repository;

import com.daejangjangi.backend.comment.domain.entity.PostComment;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.post.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {

  @Query("select pc.post from PostComment pc where pc.member = :member")
  Page<Post> findPostsByMember(@Param("member") Member member, Pageable pageable);

}
