package com.daejangjangi.backend.daejangtoon;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

public class S3ManagerTest {

  @Test
  void urlProcessor() {
    String s3Url = "https://hyensu-bucket.s3.ap-northeast-2.amazonaws.com/";
    String url = "https://hyensu-bucket.s3.ap-northeast-2.amazonaws.com/%EB%8C%80%EC%9E%A5%ED%88%B0/1/b1ab6e45-2b3e-470f-a63b-fa323b2a4eeb_10.jpg";
    String key = url.replace(s3Url, "");

    System.out.println(key);
  }
}
