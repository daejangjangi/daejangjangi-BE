package com.daejangjangi.backend.post.service;

import com.daejangjangi.backend.comment.domain.entity.PostComment;
import com.daejangjangi.backend.like.domain.entity.PostCommentLike;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.post.repository.PostCommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostCommentLikeService {

  private final PostCommentLikeRepository postCommentLikeRepository;

  /**
   * 댓글 좋아요
   *
   * @param member      회원 정보
   * @param postComment 댓글 정보
   */
  @Transactional
  public void like(Member member, PostComment postComment) {
    postCommentLikeRepository.findByMemberAndPostComment(member, postComment)
        .ifPresentOrElse(postComment::removePostCommentLike, () -> {
          postComment.addPostCommentLike(new PostCommentLike(member, postComment));
        });
  }

}
