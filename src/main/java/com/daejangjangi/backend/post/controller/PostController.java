package com.daejangjangi.backend.post.controller;

import com.daejangjangi.backend.board.domain.entity.Board;
import com.daejangjangi.backend.board.service.BoardService;
import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.like.service.PostLikeService;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.member.service.MemberService;
import com.daejangjangi.backend.post.domain.dto.PostRequestDto;
import com.daejangjangi.backend.post.domain.dto.PostResponseDto;
import com.daejangjangi.backend.post.domain.entity.Post;
import com.daejangjangi.backend.post.domain.mapper.PostMapper;
import com.daejangjangi.backend.post.service.PostService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController implements PostApi {

  private final PostService postService;
  private final MemberService memberService;
  private final BoardService boardService;
  private final PostLikeService postLikeService;

  @PostMapping
  @PreAuthorize("hasAuthority('MEMBER')")
  public ApiGlobalResponse<?> createPost(@Valid @RequestBody PostRequestDto.CreatePost request) {
    Member member = memberService.info();
    Post post = PostMapper.INSTANCE.createRequestToEntity(request);
    List<Board> boards = boardService.findByNamesIn(request.boards());
    postService.createPost(member, post, boards);
    return ApiGlobalResponse.ok();
  }

  @PutMapping
  @PreAuthorize("hasAuthority('MEMBER')")
  public ApiGlobalResponse<?> modifyPost(@Valid @RequestBody PostRequestDto.ModifyPost request) {
    Member member = memberService.info();
    Post post = PostMapper.INSTANCE.modifyRequestToEntity(request);
    List<Board> boards = boardService.findByNamesIn(request.boards());
    postService.modifyPost(member, post, boards);
    return ApiGlobalResponse.ok();
  }

  @PostMapping("/{postId}/likes")
  @PreAuthorize("hasAuthority('MEMBER')")
  public ApiGlobalResponse<?> LikePost(@PathVariable("postId") Long postId) {
    Member member = memberService.info();
    Post post = postService.findById(postId);
    postLikeService.likePostWithLock(member, post);
    return ApiGlobalResponse.ok();
  }

  @GetMapping("/{postId}")
  @PreAuthorize("hasAuthority('MEMBER')")
  public ApiGlobalResponse<?> info(@PathVariable("postId") Long postId) {
    Member member = memberService.info();
    Post post = postService.findById(postId);
    PostResponseDto.Info response = PostMapper.INSTANCE.entityToResponseDto(post, member);
    return ApiGlobalResponse.ok(response);
  }


}
