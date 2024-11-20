package com.daejangjangi.backend.global.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Slf4j
@Configuration
public class FirebaseConfig {

//  @Value("${fcm.secret-key}")
//  private String firebaseConfigPath;

  @PostConstruct
  public void init() {
    try {
      GoogleCredentials credentials = GoogleCredentials.fromStream(
          new ClassPathResource("fcm-secret-key.json").getInputStream());
      FirebaseOptions options = new FirebaseOptions.Builder()
          .setCredentials(credentials)
          .build();
      if (FirebaseApp.getApps().isEmpty()) {
        FirebaseApp.initializeApp(options);
      }
      log.info("FirebaseApp initialization Completed");

    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }

}
