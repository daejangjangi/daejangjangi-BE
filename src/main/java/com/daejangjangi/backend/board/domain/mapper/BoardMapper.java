package com.daejangjangi.backend.board.domain.mapper;

import com.daejangjangi.backend.member.domain.entity.MemberBoard;
import java.util.List;

public class BoardMapper {

  public List<String> asStringList(List<MemberBoard> boards) {
    return boards.stream().map(e -> e.getBoard().getName()).toList();
  }
}
