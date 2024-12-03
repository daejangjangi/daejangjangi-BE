package com.daejangjangi.backend.stoolanalysis.openfeign;

import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisRequestDto.ImageAnalyze;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisResponseDto.ImageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "stoolanalysis",
    url = "${spring.cloud.openfeign.client.config.stool-analysis.url}")
public interface StoolanalysisOpenFeign {

  @PostMapping(value = "/api/stool/analyze")
  ImageInfo analyzeImage(@RequestBody ImageAnalyze stoolImage);
}
