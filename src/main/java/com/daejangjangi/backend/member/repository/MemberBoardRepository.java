package com.daejangjangi.backend.member.repository;

import com.daejangjangi.backend.board.domain.entity.Board;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.member.domain.entity.MemberBoard;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberBoardRepository extends JpaRepository<MemberBoard, Long> {

  @Modifying
  @Query("DELETE FROM MemberBoard mb WHERE mb.board IN :boards and mb.member = :member")
  void deleteAllByMemberAndBoards(@Param("member") Member member,
      @Param("boards") List<Board> boards);
}
