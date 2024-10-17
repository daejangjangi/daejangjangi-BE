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

  @Transactional
  public List<Board> findByNamesIn(List<String> names) {
    List<Board> boards = boardRepository.findByNameIn(names);
    if (names.size() != boards.size()) {
      throw new NotManagedBoardException();
    }
    return boards;
  }
}
