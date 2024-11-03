package com.daejangjangi.backend.board.controller;

import com.daejangjangi.backend.board.domain.entity.Board;
import com.daejangjangi.backend.board.service.BoardService;
import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.member.service.MemberService;
import com.daejangjangi.backend.post.domain.dto.PostResponseDto;
import com.daejangjangi.backend.post.domain.dto.PostResponseDto.Info;
import com.daejangjangi.backend.post.domain.entity.Post;
import com.daejangjangi.backend.post.domain.mapper.PostMapper;
import com.daejangjangi.backend.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/boards")
public class BoardController implements BoardApi {

  private final MemberService memberService;
  private final PostService postService;
  private final BoardService boardService;

  @GetMapping
  @PreAuthorize("hasAuthority('MEMBER')")
  public ApiGlobalResponse<Page<Info>> getPostsByBoard(@RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size, @RequestParam String board) {
    Member member = memberService.info();
    Board getBoard = boardService.findByName(board);
    Pageable pageable = PageRequest.of(page, size, Direction.DESC, "id");
    Page<Post> posts = postService.getPostByBoard(getBoard, pageable);
    Page<PostResponseDto.Info> response = posts.map(
        post -> PostMapper.INSTANCE.entityToResponseDto(post, member));
    return ApiGlobalResponse.ok(response);
  }
}
