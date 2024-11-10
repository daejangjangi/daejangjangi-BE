package com.daejangjangi.backend.board.service;

import com.daejangjangi.backend.board.domain.entity.Board;
import com.daejangjangi.backend.board.repository.BoardRepository;
import com.daejangjangi.backend.post.exception.NotManagedBoardException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

  private final BoardRepository boardRepository;

  /**
   * 게시판 조회 (존재하지 않는다면 예외 발생)
   *
   * @param names 게시판
   * @return List - Board
   */
  @Transactional
  public List<Board> findByNamesIn(List<String> names) {
    List<Board> boards = boardRepository.findByNameIn(names);
    if (names.size() != boards.size()) {
      throw new NotManagedBoardException();
    }
    return boards;
  }

  /**
   * 게시판 이름 목록 중 DB에 존재하는 게시판 조회
   *
   * @param names 게시판
   * @return List - Board
   */
  public List<Board> findExistingBoardsByNames(List<String> names) {
    return boardRepository.findByNameIn(names);
  }

  /**
   * 게시판 이름으로 조회
   *
   * @param name 게시판 이름
   * @return Board
   */
  @Transactional
  public Board findByName(String name) {
    return boardRepository.findByName(name).orElseThrow(NotManagedBoardException::new);
  }
}
