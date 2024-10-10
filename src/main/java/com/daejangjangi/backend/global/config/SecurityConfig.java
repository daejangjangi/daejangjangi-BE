package com.daejangjangi.backend.global.config;

import com.daejangjangi.backend.global.config.token.TokenAuthenticationFilter;
import com.daejangjangi.backend.global.config.log.LogFilter;
import com.daejangjangi.backend.member.service.MemberService;
import com.daejangjangi.backend.token.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextHolderFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final TokenService tokenService;
  private final MemberService memberService;
  private final BCryptPasswordEncoder passwordEncoder;

  public static final String FILTER_PROCESS_URL = "/members/login";

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return web -> web.ignoring() // Security ignore
        .requestMatchers("/h2-console/**", "/favicon.ico");
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
    builder.userDetailsService(memberService).passwordEncoder(passwordEncoder);
    AuthenticationManager authenticationManager = builder.build();

    http
        .authenticationManager(authenticationManager) // authentication 설정 - 비밀번호(인코딩)
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

        // 로깅 필터 추가
        .addFilterBefore(new LogFilter(), SecurityContextHolderFilter.class)
        .addFilter(getAuthenticationFilter(authenticationManager));

    return http.build();
  }

  private TokenAuthenticationFilter getAuthenticationFilter(
      AuthenticationManager authenticationManager) {
    TokenAuthenticationFilter filter = new TokenAuthenticationFilter(authenticationManager,
        tokenService);
    filter
        .setFilterProcessesUrl(FILTER_PROCESS_URL);
    return filter;
  }
}

