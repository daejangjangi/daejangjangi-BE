package com.daejangjangi.backend.daejangtoon.controller;

import com.daejangjangi.backend.daejangtoon.domain.dto.DaejangtoonRequestDto;
import com.daejangjangi.backend.daejangtoon.domain.dto.DaejangtoonResponseDto;
import com.daejangjangi.backend.daejangtoon.domain.entity.Daejangtoon;
import com.daejangjangi.backend.daejangtoon.domain.mapper.DaejangtoonMapper;
import com.daejangjangi.backend.daejangtoon.service.DaejangtoonService;
import com.daejangjangi.backend.global.response.ApiGlobalResponse;
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
@RequestMapping("/api/v1/daejangtoons")
@RequiredArgsConstructor
public class DaejangtoonController implements DaejangtoonApi {

  private final DaejangtoonService daejangtoonService;

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ApiGlobalResponse<Null> register(
      @Valid @RequestPart DaejangtoonRequestDto.Register request,
      @RequestPart MultipartFile profileImage,
      @RequestPart List<MultipartFile> toonImages
  ) {
    Daejangtoon daejangtoon = DaejangtoonMapper.INSTANCE.registerToEntity(request);
    daejangtoonService.save(daejangtoon, profileImage, toonImages);
    return ApiGlobalResponse.ok();
  }

  @PreAuthorize("hasAuthority('MEMBER')")
  @GetMapping
  public ApiGlobalResponse<List<DaejangtoonResponseDto.Daejangtoons>> daejangtoons() {
    List<Daejangtoon> daejangtoons = daejangtoonService.daejangtoons();
    List<DaejangtoonResponseDto.Daejangtoons> response
        = DaejangtoonMapper.INSTANCE.entityToDaejangtoonsResponse(daejangtoons);
    return ApiGlobalResponse.ok(response);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping("/{chapter}")
  public ApiGlobalResponse<Null> remove(@PathVariable("chapter") Integer chapter) {
    daejangtoonService.remove(chapter);
    return ApiGlobalResponse.ok();
  }
}
