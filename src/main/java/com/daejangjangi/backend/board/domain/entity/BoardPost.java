package com.daejangjangi.backend.board.domain.entity;

import com.daejangjangi.backend.post.domain.entity.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "boards_posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardPost {

  @Builder
  public BoardPost(Board board, Post post) {
    this.board = board;
    this.post = post;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "board_post_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "board_id")
  private Board board;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "post_id")
  private Post post;

}
