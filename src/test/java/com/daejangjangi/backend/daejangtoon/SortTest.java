package com.daejangjangi.backend.daejangtoon;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.daejangjangi.backend.daejangtoon.domain.entity.DaejangtoonImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SortTest {

  @Test
  @DisplayName("이미지 순서를 기반으로 정렬하기")
  void SortByOrder() {
    List<DaejangtoonImage> images = new ArrayList<>();
    images.add(DaejangtoonImage.builder().order(2).image("2컷").build());
    images.add(DaejangtoonImage.builder().order(4).image("4컷").build());
    images.add(DaejangtoonImage.builder().order(3).image("3컷").build());
    images.add(DaejangtoonImage.builder().order(6).image("6컷").build());
    images.add(DaejangtoonImage.builder().order(5).image("5컷").build());
    images.add(DaejangtoonImage.builder().order(9).image("9컷").build());
    images.add(DaejangtoonImage.builder().order(7).image("7컷").build());
    images.add(DaejangtoonImage.builder().order(10).image("10컷").build());
    images.add(DaejangtoonImage.builder().order(8).image("8컷").build());
    images.add(DaejangtoonImage.builder().order(1).image("1컷").build());
    images.sort(Comparator.comparing(DaejangtoonImage::getOrder));
    assertEquals("1컷", images.get(0).getImage());
  }
}
