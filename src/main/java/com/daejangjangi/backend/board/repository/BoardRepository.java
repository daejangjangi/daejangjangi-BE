package com.daejangjangi.backend.board.repository;

import com.daejangjangi.backend.board.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

  List<Board> findByNameIn(List<String> names);
}
