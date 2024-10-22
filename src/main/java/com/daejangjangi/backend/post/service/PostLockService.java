package com.daejangjangi.backend.post.service;

import com.daejangjangi.backend.like.service.PostLikeService;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.post.domain.entity.Post;
import com.daejangjangi.backend.post.repository.PostLockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLockService {

  private final PostLockRepository postLockRepository;
  private final PostLikeService postLikeService;

  @Transactional
  public void likePostWithLock(Member member, Post post) {
    try {
      postLockRepository.getLock(post.getId().toString());
      postLikeService.like(member, post);
    } finally {
      postLockRepository.releaseLock(post.getId().toString());
    }
  }

}
