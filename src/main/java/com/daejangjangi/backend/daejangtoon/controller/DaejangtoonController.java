package com.daejangjangi.backend.daejangtoon.controller;

import com.daejangjangi.backend.daejangtoon.domain.dto.DaejangtoonRequestDto;
import com.daejangjangi.backend.daejangtoon.domain.dto.DaejangtoonResponseDto;
import com.daejangjangi.backend.daejangtoon.domain.entity.Daejangtoon;
import com.daejangjangi.backend.daejangtoon.domain.entity.DaejangtoonChapter;
import com.daejangjangi.backend.daejangtoon.domain.mapper.DaejangtoonMapper;
import com.daejangjangi.backend.daejangtoon.service.DaejangtoonChapterService;
import com.daejangjangi.backend.daejangtoon.service.DaejangtoonService;
import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.like.service.DaejangtoonLikeService;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.member.service.MemberService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/daejangtoons")
@RequiredArgsConstructor
public class DaejangtoonController implements DaejangtoonApi {

  private final MemberService memberService;
  private final DaejangtoonService daejangtoonService;
  private final DaejangtoonChapterService daejangtoonChapterService;
  private final DaejangtoonLikeService daejangtoonLikeService;

  //  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping
  public ApiGlobalResponse<Long> register(
      @Valid @RequestBody DaejangtoonRequestDto.Register request
  ) {
    Daejangtoon daejangtoon = DaejangtoonMapper.INSTANCE.registerToEntity(request);
    return ApiGlobalResponse.ok(daejangtoonService.save(daejangtoon));
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping(value = "/{daejangtoonId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ApiGlobalResponse<Null> registerChapter(
      @PathVariable("daejangtoonId") Long daejangtoonId,
      @Valid @RequestPart DaejangtoonRequestDto.RegisterChapter request,
      @RequestPart MultipartFile profileImage,
      @RequestPart List<MultipartFile> toonImages
  ) {
    Daejangtoon daejangtoon = daejangtoonService.findById(daejangtoonId);
    DaejangtoonChapter chapter = DaejangtoonMapper.INSTANCE.registerChapterToEntity(request);
    daejangtoonChapterService.save(daejangtoon, chapter, profileImage, toonImages);
    return ApiGlobalResponse.ok();
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping("/{daejangtoonId}/{chapter}")
  public ApiGlobalResponse<Null> remove(
      @PathVariable("daejangtoonId") Long daejangtoonId,
      @PathVariable("chapter") Integer chapter
  ) {
    Daejangtoon daejangtoon = daejangtoonService.findById(daejangtoonId);
    DaejangtoonChapter daejangtoonChapter =
        daejangtoonChapterService.getChapter(daejangtoon, chapter);
    daejangtoonChapterService.remove(daejangtoonChapter);
    return ApiGlobalResponse.ok();
  }

  @PreAuthorize("hasAuthority('MEMBER')")
  @GetMapping("/{daejangtoonId}")
  public ApiGlobalResponse<DaejangtoonResponseDto.Daejangtoon> info(
      @PathVariable Long daejangtoonId
  ) {
    Daejangtoon daejangtoon = daejangtoonService.findById(daejangtoonId);
    DaejangtoonResponseDto.Daejangtoon response
        = DaejangtoonMapper.INSTANCE.entityToDaejangtoonResponse(daejangtoon);
    return ApiGlobalResponse.ok(response);
  }

  @PreAuthorize("hasAuthority('MEMBER')")
  @GetMapping("/{daejangtoonId}/recent")
  public ApiGlobalResponse<DaejangtoonResponseDto.DaejangtoonChapters> recentDaejangtoon(
      @PathVariable("daejangtoonId") Long daejangtoonId
  ) {
    DaejangtoonChapter chapter = daejangtoonChapterService.recent(daejangtoonId);
    DaejangtoonResponseDto.DaejangtoonChapters response
        = DaejangtoonMapper.INSTANCE.entityToChaptersResponse(chapter);
    return ApiGlobalResponse.ok(response);
  }

  @PreAuthorize("hasAuthority('MEMBER')")
  @GetMapping("/{daejangtoonId}/{chapter}")
  public ApiGlobalResponse<DaejangtoonResponseDto.DaejangtoonChapter> chapter(
      @PathVariable("daejangtoonId") Long daejangtoonId,
      @PathVariable("chapter") Integer chapter
  ) {
    Daejangtoon daejangtoon = daejangtoonService.findById(daejangtoonId);
    DaejangtoonChapter daejangtoonChapter =
        daejangtoonChapterService.getChapterWithHit(daejangtoon, chapter);
    DaejangtoonResponseDto.DaejangtoonChapter response
        = DaejangtoonMapper.INSTANCE.entityToChapterResponse(daejangtoonChapter);
    return ApiGlobalResponse.ok(response);
  }

  @PreAuthorize("hasAuthority('MEMBER')")
  @PostMapping("/{daejangtoonId}/{chapter}/likes")
  public ApiGlobalResponse<Null> LikeDaejangtoon(
      @PathVariable Long daejangtoonId,
      @PathVariable Integer chapter
  ) {
    Member member = memberService.info();
    Daejangtoon daejangtoon = daejangtoonService.findById(daejangtoonId);
    DaejangtoonChapter daejangtoonChapter =
        daejangtoonChapterService.getChapter(daejangtoon, chapter);
    daejangtoonLikeService.like(member, daejangtoonChapter);
    return ApiGlobalResponse.ok();
  }
}
