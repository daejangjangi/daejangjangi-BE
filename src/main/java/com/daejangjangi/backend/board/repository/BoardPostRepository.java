package com.daejangjangi.backend.board.repository;

import com.daejangjangi.backend.board.domain.entity.Board;
import com.daejangjangi.backend.board.domain.entity.BoardPost;
import com.daejangjangi.backend.post.domain.entity.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardPostRepository extends JpaRepository<BoardPost, Long> {

  @Modifying
  @Query("DELETE FROM BoardPost bp WHERE bp.board IN :deleted and bp.post = :post")
  void deleteAllByBoardAndPost(@Param("deleted") List<Board> deleted, @Param("post") Post post);
}
