package com.daejangjangi.backend.like.service;

import com.daejangjangi.backend.like.domain.entity.PostLike;
import com.daejangjangi.backend.like.repository.PostLikeRepository;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.post.domain.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

  private final PostLikeRepository postLikeRepository;

  /**
   * 게시글 좋아요
   *
   * @param member 회원 정보
   * @param post   게시글 정보
   */
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void like(Member member, Post post) {
    postLikeRepository.findByMemberAndPost(member, post)
        .ifPresentOrElse(postLike -> {
          post.updateLikeCount(-1);
          postLikeRepository.delete(postLike);
        }, () -> {
          post.updateLikeCount(1);
          postLikeRepository.save(new PostLike(member, post));
        });
  }

}
