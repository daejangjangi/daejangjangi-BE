package com.daejangjangi.backend.post.repository;

import com.daejangjangi.backend.comment.domain.entity.PostComment;
import com.daejangjangi.backend.like.domain.entity.PostCommentLike;
import com.daejangjangi.backend.member.domain.entity.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentLikeRepository extends JpaRepository<PostCommentLike, Long> {

  Optional<PostCommentLike> findByMemberAndPostComment(Member member, PostComment postComment);

  List<PostCommentLike> findByMember(Member member);
}
