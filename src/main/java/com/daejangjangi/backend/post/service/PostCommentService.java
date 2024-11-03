package com.daejangjangi.backend.post.service;

import com.daejangjangi.backend.comment.domain.entity.PostComment;
import com.daejangjangi.backend.comment.exception.NotCommentAuthor;
import com.daejangjangi.backend.post.domain.entity.Post;
import com.daejangjangi.backend.post.repository.PostCommentRepository;
import com.daejangjangi.backend.member.domain.entity.Member;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostCommentService {

  private final PostCommentRepository postCommentRepository;

  /**
   * 댓글 저장
   *
   * @param postComment   댓글 정보
   * @param parentComment 부모 댓글 정보
   */
  public void save(PostComment postComment, PostComment parentComment) {
    if (Objects.nonNull(parentComment)) {
      parentComment.addChildComment(postComment);
      postComment.updateParent(parentComment);
    }
    postCommentRepository.save(postComment);
  }

  /**
   * 댓글 수정
   *
   * @param member         로그인한 사용자 정보
   * @param oldPostComment 수정 전 댓글 정보
   * @param newPostComment 수정 후 댓글 정보
   */
  @Transactional
  public void update(Member member, PostComment oldPostComment, PostComment newPostComment) {
    validateAuthor(oldPostComment, member);
    oldPostComment.updateContent(newPostComment.getContent());
  }

  /**
   * 댓글 삭제
   *
   * @param member      로그인한 사용자 정보
   * @param postComment 삭제할 댓글 정보
   */
  @Transactional
  public void delete(Member member, PostComment postComment) {
    validateAuthor(postComment, member);

    if (postComment.getParent() == null) {
      deleteRootComment(postComment);
    } else {
      deleteComment(postComment);
    }
  }

  /**
   * 자식 댓글 삭제
   *
   * @param postComment 삭제할 게시글 댓글 정보
   */
  protected void deleteComment(PostComment postComment) {
    PostComment parent = postComment.getParent();
    parent.getChildren().remove(postComment);
    postComment.updateParent(null);

    if (parent.getChildren().isEmpty() && parent.isDeleted()) {
      postCommentRepository.delete(parent);
    }
  }

  /**
   * 최상위 부모 댓글 삭제
   *
   * @param postComment 삭제할 게시글 댓글 정보
   */
  private void deleteRootComment(PostComment postComment) {
    if (postComment.getChildren().isEmpty()) {
      postCommentRepository.delete(postComment);
    } else {
      postComment.updateDeleted();
    }
  }

  /**
   * 댓글 작성자 검증
   *
   * @param postComment 댓글 정보
   * @param member      로그인한 사용자 정보
   */
  private void validateAuthor(PostComment postComment, Member member) {
    if (!Objects.equals(postComment.getMember(), member)) {
      throw new NotCommentAuthor();
    }
  }

  /**
   * 댓글 단 게시글 조회
   *
   * @param member   회원 정보
   * @param pageable 페이징 정보
   * @return Page<Post>
   */
  public Page<Post> findPostsCommentedByMember(Member member, Pageable pageable) {
    return postCommentRepository.findPostsByMember(member, pageable);
  }
}
