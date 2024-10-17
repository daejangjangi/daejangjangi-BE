package com.daejangjangi.backend.post.controller;

import com.daejangjangi.backend.board.domain.entity.Board;
import com.daejangjangi.backend.board.service.BoardService;
import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.member.service.MemberService;
import com.daejangjangi.backend.post.domain.dto.PostRequestDto;
import com.daejangjangi.backend.post.domain.entity.Post;
import com.daejangjangi.backend.post.domain.mapper.PostMapper;
import com.daejangjangi.backend.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@Tag(name = "Post (게시글) API", description = "게시글 관련 API")
public class PostController {

  private final PostService postService;
  private final MemberService memberService;
  private final BoardService boardService;

  @PostMapping
  @PreAuthorize("hasAuthority('MEMBER')")
  @Operation(summary = "게시글 생성 API")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "게시글 생성 성공"),
      @ApiResponse(responseCode = "401", description = "인증 필요한 API")
  })
  public ApiGlobalResponse<?> createPost(@Valid @RequestBody PostRequestDto.CreatePost request) {
    Member member = memberService.info();
    Post post = PostMapper.INSTANCE.createRequestToEntity(request);
    List<Board> boards = boardService.findByNamesIn(request.boards());
    postService.createPost(member, post, boards);
    return ApiGlobalResponse.ok();
  }

  @PutMapping
  @PreAuthorize("hasAuthority('MEMBER')")
  @Operation(summary = "게시글 수정 API")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "게시글 수정 성공"),
      @ApiResponse(responseCode = "403", description = "게시글 작성자가 아니므로 수정 실패"),
      @ApiResponse(responseCode = "401", description = "인증 필요한 API")
  })
  public ApiGlobalResponse<?> modifyPost(@Valid @RequestBody PostRequestDto.ModifyPost request) {
    Member member = memberService.info();
    Post post = PostMapper.INSTANCE.modifyRequestToEntity(request);
    List<Board> boards = boardService.findByNamesIn(request.boards());
    postService.modifyPost(member, post, boards);
    return ApiGlobalResponse.ok();
  }

}
