package com.daejangjangi.backend.post.service;

import com.daejangjangi.backend.comment.domain.entity.PostComment;
import com.daejangjangi.backend.comment.exception.NotCommentAuthor;
import com.daejangjangi.backend.fcm.repository.FcmTokenRepository;
import com.daejangjangi.backend.post.domain.entity.Post;
import com.daejangjangi.backend.post.repository.PostCommentRepository;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.notification.domain.enums.NotificationType;
import com.daejangjangi.backend.notification.repository.NotificationRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostCommentService {

  private final PostCommentRepository postCommentRepository;
  private final FcmTokenRepository fcmTokenRepository;
  private final NotificationRepository notificationRepository;
  private final String COMMENT_NOTIFICATION_BODY = "1개의 댓글이 달렸어요 : ";

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
    if (!postComment.getMember().equals(postComment.getPost().getMember())) {
      fcmTokenRepository.findByMember(postComment.getPost().getMember()).forEach(token -> {
        saveCommentNotification(postComment);
        sendNotification(token.getFcmToken(), postComment.getPost().getTitle(),
            COMMENT_NOTIFICATION_BODY + postComment.getContent());
      });
    }

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

  /**
   * 푸시 알림 전송
   *
   * @param token 대상 디바이스의 FCM 토큰
   * @param title 알림 제목
   * @param body  알림 내용
   * @throws FirebaseMessagingException FCM 전송 중 오류 발생 시
   */
  @Async
  protected void sendNotification(String token, String title, String body) {
    Notification notification = Notification.builder()
        .setTitle(title)
        .setBody(body)
        .build();

    Message message = Message.builder()
        .setToken(token)
        .setNotification(notification)
        .build();

    try {
      FirebaseMessaging.getInstance().send(message);
    } catch (FirebaseMessagingException e) {
      fcmTokenRepository.deleteByFcmToken(token);
    }
  }

  /**
   * 알림 정보 저장
   *
   * @param comment 댓글 정보
   */
  private void saveCommentNotification(PostComment comment) {
    com.daejangjangi.backend.notification.domain.entity.Notification notification =
        com.daejangjangi.backend.notification.domain.entity.Notification.builder()
            .receiver(comment.getPost().getMember())
            .title(comment.getPost().getTitle())
            .body(comment.getContent())
            .contentId(comment.getPost().getId())
            .type(NotificationType.COMMENT).build();
    notificationRepository.save(notification);
  }
}
