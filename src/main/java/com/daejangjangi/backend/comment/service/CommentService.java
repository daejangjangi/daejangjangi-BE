package com.daejangjangi.backend.comment.service;

import com.daejangjangi.backend.comment.domain.entity.PostComment;
import com.daejangjangi.backend.comment.exception.NotFoundComment;
import com.daejangjangi.backend.comment.repository.PostCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final PostCommentRepository postCommentRepository;

  /**
   * id로 댓글 찾기
   *
   * @param id 댓글 아이디
   * @return PostComment
   */
  public PostComment findById(Long id) {
    return postCommentRepository.findById(id).orElseThrow(NotFoundComment::new);
  }

}
