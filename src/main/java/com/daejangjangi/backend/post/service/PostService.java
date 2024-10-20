package com.daejangjangi.backend.post.service;

import com.daejangjangi.backend.board.domain.entity.Board;
import com.daejangjangi.backend.board.domain.entity.BoardPost;
import com.daejangjangi.backend.board.repository.BoardPostRepository;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.post.domain.entity.Post;
import com.daejangjangi.backend.post.exception.NotFoundPostException;
import com.daejangjangi.backend.post.exception.NotPostAuthorException;
import com.daejangjangi.backend.post.repository.PostRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;
  private final BoardPostRepository boardPostRepository;

  /**
   * 게시글 생성
   *
   * @param member 회원 정보
   * @param post   게시글 정보
   * @param boards 게시글 카테고리
   */
  @Transactional
  public void createPost(Member member, Post post, List<Board> boards) {
    post.updateMember(member);
    Post savePost = postRepository.save(post);
    savePost.addBoards(saveBoardPosts(post, boards));
  }

  /**
   * 게시글 작성자 확인
   *
   * @param member   회원 정보
   * @param authorId 게시글 작성자 id
   */
  public void checkPostAuthor(Member member, Long authorId) {
    if (!Objects.equals(member.getId(), authorId)) {
      throw new NotPostAuthorException();
    }
  }

  /**
   * 게시글 카테고리 저장
   *
   * @param post   게시글 정보
   * @param boards 게시글 카테고리 정보
   * @return List - BoardPost
   */
  public List<BoardPost> saveBoardPosts(Post post, List<Board> boards) {
    List<BoardPost> boardPosts = new ArrayList<>();

    for (Board board : boards) {
      BoardPost boardPost = BoardPost.builder()
          .board(board)
          .post(post).build();
      boardPosts.add(boardPost);
    }
    return boardPostRepository.saveAll(boardPosts);
  }

  /**
   * 게시글 수정
   *
   * @param member    회원 정보
   * @param post      수정된 게시글 정보
   * @param newBoards 새롭게 등록한 게시글 카테고리
   */
  @Transactional
  public void modifyPost(Member member, Post post, List<Board> newBoards) {
    Post originPost = postRepository.findById(post.getId()).orElseThrow(NotFoundPostException::new);
    checkPostAuthor(member, originPost.getMember().getId());
    List<Board> originBoardPosts = originPost.getBoards().stream().map(BoardPost::getBoard)
        .toList();
    originPost.updatePost(post);

    List<Board> addedBoards = new ArrayList<>();
    List<Board> removedBoards = new ArrayList<>();

    newBoards.forEach(board -> {
      if (!originBoardPosts.contains(board)) {
        addedBoards.add(board);
      }
    });

    if (!addedBoards.isEmpty()) {
      saveBoardPosts(originPost, addedBoards);
    }

    originBoardPosts.forEach(board -> {
      if (!newBoards.contains(board)) {
        removedBoards.add(board);
      }
    });

    if (!removedBoards.isEmpty()) {
      boardPostRepository.deleteAllByBoardAndPost(removedBoards, post);
    }
  }
}
