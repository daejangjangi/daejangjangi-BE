package com.daejangjangi.backend.post.controller;

import com.daejangjangi.backend.board.domain.entity.Board;
import com.daejangjangi.backend.board.service.BoardService;
import com.daejangjangi.backend.comment.domain.entity.PostComment;
import com.daejangjangi.backend.comment.service.CommentService;
import com.daejangjangi.backend.post.domain.dto.PostResponseDto.CommentInfo;
import com.daejangjangi.backend.post.domain.dto.PostResponseDto.DetailInfo;
import com.daejangjangi.backend.post.domain.dto.PostResponseDto.Info;
import com.daejangjangi.backend.post.service.PostCommentLikeService;
import com.daejangjangi.backend.post.service.PostCommentService;
import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.post.service.PostLikeService;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.member.service.MemberService;
import com.daejangjangi.backend.post.domain.dto.PostRequestDto;
import com.daejangjangi.backend.post.domain.dto.PostResponseDto;
import com.daejangjangi.backend.post.domain.entity.Post;
import com.daejangjangi.backend.post.domain.mapper.PostCommentMapper;
import com.daejangjangi.backend.post.domain.mapper.PostMapper;
import com.daejangjangi.backend.post.service.PostService;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils.Null;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
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
  private final CommentService commentService;
  private final PostCommentService postCommentService;
  private final PostCommentLikeService postCommentLikeService;

  @PostMapping
  @PreAuthorize("hasAuthority('MEMBER')")
  public ApiGlobalResponse<Null> createPost(@Valid @RequestBody PostRequestDto.CreatePost request) {
    Member member = memberService.info();
    Post post = PostMapper.INSTANCE.createRequestToEntity(request);
    List<Board> boards = boardService.findByNamesIn(request.boards());
    postService.createPost(member, post, boards);
    return ApiGlobalResponse.ok();
  }

  @PutMapping
  @PreAuthorize("hasAuthority('MEMBER')")
  public ApiGlobalResponse<Null> modifyPost(@Valid @RequestBody PostRequestDto.ModifyPost request) {
    Member member = memberService.info();
    Post post = PostMapper.INSTANCE.modifyRequestToEntity(request);
    List<Board> boards = boardService.findByNamesIn(request.boards());
    postService.modifyPost(member, post, boards);
    return ApiGlobalResponse.ok();
  }

  @PostMapping("/{postId}/likes")
  @PreAuthorize("hasAuthority('MEMBER')")
  public ApiGlobalResponse<Null> LikePost(@PathVariable("postId") Long postId) {
    Member member = memberService.info();
    Post post = postService.findById(postId);
    postLikeService.likePostWithLock(member, post);
    return ApiGlobalResponse.ok();
  }

  @GetMapping("/{postId}")
  @PreAuthorize("hasAuthority('MEMBER')")
  public ApiGlobalResponse<DetailInfo> info(@PathVariable("postId") Long postId) {
    Post post = postService.findById(postId);
    post = postService.updateHit(post);
    Member member = memberService.info();
    List<PostResponseDto.CommentInfo> commentInfos = new ArrayList<>();
    post.getComments().forEach(comment -> {
      PostResponseDto.CommentInfo info = CommentInfo.builder()
          .id(comment.getId())
          .likes((long) comment.getLikes().size())
          .createdAt(comment.getCreatedAt())
          .content(comment.getContent())
          .nickname(comment.getMember().getNickname())
          .isDeleted(comment.isDeleted())
          .isLiked(comment.getLikes().stream().anyMatch(like -> like.getMember().equals(member)))
          .isAuthor(comment.getMember().equals(member)).build();

      if (comment.getParent() == null) {
        comment.getChildren().forEach(child -> {
          PostResponseDto.CommentInfo childInfo = CommentInfo.builder()
              .id(child.getId())
              .likes((long) child.getLikes().size())
              .createdAt(child.getCreatedAt())
              .content(child.getContent())
              .nickname(child.getMember().getNickname())
              .isDeleted(child.isDeleted())
              .isLiked(
                  child.getLikes().stream().anyMatch(like -> like.getMember().equals(member)))
              .isAuthor(child.getMember().equals(member)).build();
          info.addCommentInfo(childInfo);
        });
        commentInfos.add(info);
      }
    });
    PostResponseDto.DetailInfo response = PostMapper.INSTANCE.entityToPostDetailInfoResponse(post,
        member, commentInfos);
    return ApiGlobalResponse.ok(response);
  }

  @PostMapping("/comments")
  @PreAuthorize("hasAuthority('MEMBER')")
  public ApiGlobalResponse<Null> creatPostComment(
      @Valid @RequestBody PostRequestDto.CreatePostComment request) {
    Member member = memberService.info();
    Post post = postService.findById(request.postId());
    PostComment parentComment = Objects.isNull(request.parentCommentId()) ? null
        : commentService.findById(request.parentCommentId());
    PostComment postComment = PostCommentMapper.INSTANCE.createRequestToEntity(request, post,
        member);
    postCommentService.save(postComment, parentComment);
    return ApiGlobalResponse.ok();
  }

  @PutMapping("/comments")
  @PreAuthorize("hasAuthority('MEMBER')")
  public ApiGlobalResponse<Null> modifyPostComment(
      @Valid @RequestBody PostRequestDto.ModifyPostComment request) {
    Member member = memberService.info();
    PostComment oldPostComment = commentService.findById(request.id());
    PostComment newPostComment = PostCommentMapper.INSTANCE.modifyRequestToEntity(request);
    postCommentService.update(member, oldPostComment, newPostComment);
    return ApiGlobalResponse.ok();
  }

  @DeleteMapping("/comments/{commentId}")
  @PreAuthorize("hasAuthority('MEMBER')")
  public ApiGlobalResponse<Null> deletePostComment(@PathVariable Long commentId) {
    Member member = memberService.info();
    PostComment postComment = commentService.findById(commentId);
    postCommentService.delete(member, postComment);
    return ApiGlobalResponse.ok();
  }

  @PostMapping("/comments/{commentId}/likes")
  @PreAuthorize("hasAuthority('MEMBER')")
  public ApiGlobalResponse<Null> likeCommentPost(@PathVariable("commentId") Long commentId) {
    Member member = memberService.info();
    PostComment postComment = commentService.findById(commentId);
    postCommentLikeService.like(member, postComment);
    return ApiGlobalResponse.ok();
  }

  @GetMapping("/my-posts")
  @PreAuthorize("hasAuthority('MEMBER')")
  public ApiGlobalResponse<Page<Info>> findPostsByMember(
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
    Member member = memberService.info();
    Pageable pageable = PageRequest.of(page, size, Direction.DESC, "id");
    Page<Post> posts = postService.findPostsByMember(member, pageable);
    Page<PostResponseDto.Info> response = posts.map(PostMapper.INSTANCE::entityToPostInfoResponse);
    return ApiGlobalResponse.ok(response);
  }

  @GetMapping("/commented-posts")
  @PreAuthorize("hasAuthority('MEMBER')")
  public ApiGlobalResponse<Page<PostResponseDto.Info>> findPostsCommentedByMember(
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
    Member member = memberService.info();
    Pageable pageable = PageRequest.of(page, size, Direction.DESC, "id");
    Page<Post> posts = postCommentService.findPostsCommentedByMember(member, pageable);
    Page<PostResponseDto.Info> response = posts.map(PostMapper.INSTANCE::entityToPostInfoResponse);
    return ApiGlobalResponse.ok(response);
  }

  @GetMapping("/search")
  @PreAuthorize("hasAuthority('MEMBER')")
  public ApiGlobalResponse<Page<Info>> findPostsByKeyword(
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
      @RequestParam String keyword) {
    Pageable pageable = PageRequest.of(page, size, Direction.DESC, "id");
    Page<Post> posts = postService.findPostsByKeyword(keyword, pageable);
    Page<PostResponseDto.Info> response = posts.map(PostMapper.INSTANCE::entityToPostInfoResponse);
    return ApiGlobalResponse.ok(response);
  }

  @DeleteMapping("/{postId}")
  @PreAuthorize("hasAuthority('MEMBER')")
  public ApiGlobalResponse<Null> deletePost(@PathVariable("postId") Long postId) {
    Member member = memberService.info();
    Post post = postService.findById(postId);
    postService.deletePost(member, post);
    return ApiGlobalResponse.ok();
  }

  @GetMapping("/hot")
  @PreAuthorize("hasAuthority('MEMBER')")
  public ApiGlobalResponse<Page<PostResponseDto.Info>> findHotPosts(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size, Direction.DESC, "id");
    Page<Post> posts = postService.findHotPosts(pageable);
    Page<PostResponseDto.Info> response = posts.map(PostMapper.INSTANCE::entityToPostInfoResponse);
    return ApiGlobalResponse.ok(response);
  }

}
