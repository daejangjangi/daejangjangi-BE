package com.daejangjangi.backend.post.domain.mapper;

import com.daejangjangi.backend.comment.domain.entity.PostComment;
import java.util.List;

public class CommentMapper {

  public Long asLong(List<PostComment> postComments) {
    return postComments.stream().filter(comment -> !comment.isDeleted()).count();
  }

}
