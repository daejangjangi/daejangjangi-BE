package com.daejangjangi.backend.reels.controller;

import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.reels.domain.dto.ReelsRequestDto;
import com.daejangjangi.backend.reels.domain.dto.ReelsResponseDto;
import com.daejangjangi.backend.reels.domain.entity.Reels;
import com.daejangjangi.backend.reels.domain.mapper.ReelsMapper;
import com.daejangjangi.backend.reels.service.ReelsService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils.Null;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/reels")
@RequiredArgsConstructor
public class ReelsController implements ReelsApi {

  private final ReelsService reelsService;

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ApiGlobalResponse<Null> register(
      @Valid @RequestPart ReelsRequestDto.Register request,
      @RequestPart MultipartFile reelsVideo
  ) {
    Reels reels = ReelsMapper.INSTANCE.requestToEntity(request);
    reelsService.register(reels, reelsVideo);
    return ApiGlobalResponse.ok();
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping("/{reelsId}")
  public ApiGlobalResponse<Null> remove(
      @PathVariable("reelsId") Long reelsId
  ) {
    Reels reels = reelsService.findById(reelsId);
    reelsService.remove(reels);
    return ApiGlobalResponse.ok();
  }

  @PreAuthorize("hasAuthority('MEMBER')")
  @GetMapping
  public ApiGlobalResponse<ReelsResponseDto.ReelsInfoList> list() {
    List<Reels> reelsList = reelsService.findAll();
    List<ReelsResponseDto.ReelsInfo> reelsInfoList = ReelsMapper.INSTANCE.entityToResponse(
        reelsList);
    ReelsResponseDto.ReelsInfoList response = new ReelsResponseDto.ReelsInfoList(reelsInfoList);
    return ApiGlobalResponse.ok(response);
  }

  @PreAuthorize("hasAuthority('MEMBER')")
  @GetMapping("/{reelsId}")
  public ApiGlobalResponse<ReelsResponseDto.ReelsInfo> info(
      @PathVariable("reelsId") Long reelsId
  ) {
    Reels reels = reelsService.findById(reelsId);
    ReelsResponseDto.ReelsInfo response = ReelsMapper.INSTANCE.reelsToReelsInfo(reels);
    return ApiGlobalResponse.ok(response);
  }
}
