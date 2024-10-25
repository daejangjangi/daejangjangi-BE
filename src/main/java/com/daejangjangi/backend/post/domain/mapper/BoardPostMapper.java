package com.daejangjangi.backend.post.domain.mapper;

import com.daejangjangi.backend.board.domain.entity.BoardPost;
import java.util.List;

public class BoardPostMapper {

  public List<String> asStringList(List<BoardPost> boardPosts) {
    return boardPosts.stream().map(boardPost -> boardPost.getBoard().getName()).toList();
  }

}