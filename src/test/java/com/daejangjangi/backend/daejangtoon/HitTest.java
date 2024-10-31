package com.daejangjangi.backend.daejangtoon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.daejangjangi.backend.daejangtoon.domain.entity.Daejangtoon;
import com.daejangjangi.backend.daejangtoon.domain.entity.DaejangtoonChapter;
import com.daejangjangi.backend.daejangtoon.domain.enums.Yoil;
import com.daejangjangi.backend.daejangtoon.repository.DaejangtoonChapterRepository;
import com.daejangjangi.backend.daejangtoon.service.DaejangtoonChapterService;
import com.daejangjangi.backend.daejangtoon.service.DaejangtoonService;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootTest
@SuppressWarnings("NonAsciiCharacters")
public class HitTest {

  @Autowired
  private DaejangtoonService daejangtoonService;

  @Autowired
  private DaejangtoonChapterService chapterService;

  @Autowired
  private DaejangtoonChapterRepository chapterRepository;

  @Disabled
  @BeforeEach
  void init() {
    String memberId = "";
    Authentication authentication = Mockito.mock(Authentication.class);
    when(authentication.getName()).thenReturn(memberId);
    SecurityContext context = Mockito.mock(SecurityContext.class);
    Mockito.when(context.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(context);

    Daejangtoon daejangtoon = Daejangtoon.builder()
        .title("대장툰")
        .overview("어쩌구 저쩌구")
        .yoil(Yoil.월)
        .build();
    daejangtoonService.save(daejangtoon);

    DaejangtoonChapter daejangtoonChapter = DaejangtoonChapter.builder()
        .chapter(1)
        .title("테스트")
        .profile("테스트")
        .build();
    daejangtoon.addChapter(daejangtoonChapter);
    daejangtoonChapter.updateParent(daejangtoon);
    daejangtoonChapter.addImages(new ArrayList<>());
    chapterRepository.save(daejangtoonChapter);
  }

  @Disabled
  @Test
  void 동시에_100개_요청() throws InterruptedException {
    Daejangtoon daejangtoon = daejangtoonService.findById(1L);
    int threadCount = 100;
    final ExecutorService executorService = Executors.newFixedThreadPool(32);
    final CountDownLatch countDownLatch = new CountDownLatch(threadCount);
    for (int i = 0; i < threadCount; i++) {
      executorService.submit(() -> {
        try {
          chapterService.getChapterWithHit(daejangtoon, 1);
        } finally {
          countDownLatch.countDown();
        }
      });
    }
    countDownLatch.await();

    final DaejangtoonChapter chapter = chapterRepository.findByDaejangtoonAndChapter(daejangtoon, 1)
        .orElseThrow();

    assertEquals(100L, chapter.getHit());
  }

}
