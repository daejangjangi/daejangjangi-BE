package com.daejangjangi.backend.global.config;

import com.daejangjangi.backend.global.config.log.LogFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextHolderFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return web -> web.ignoring() // Security ignore
        .requestMatchers("/h2-console/**", "/favicon.ico");
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
        .csrf(AbstractHttpConfigurer::disable) // csrf 비활성화
        .cors(AbstractHttpConfigurer::disable) // cors 비활성화
        .httpBasic(AbstractHttpConfigurer::disable) // 기본 인증 로그인 비활성화
        .formLogin(AbstractHttpConfigurer::disable) // 기본 login form 비활성화
        .logout(AbstractHttpConfigurer::disable) // 기본 logout 비활성화
        .headers(c -> c
            .frameOptions(
                HeadersConfigurer.FrameOptionsConfig::sameOrigin)) // X-Frame-Options sameOrigin 제한
        .sessionManagement(c -> c
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 비활성화

        .authorizeHttpRequests(e -> e.anyRequest().permitAll())

        // 로깅 필터 추가
        .addFilterBefore(new LogFilter(), SecurityContextHolderFilter.class)
    ;

    return http.build();
  }
}

