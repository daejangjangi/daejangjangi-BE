package com.daejangjangi.backend.member.service;

import com.daejangjangi.backend.category.domain.entity.Category;
import com.daejangjangi.backend.disease.domain.entity.Disease;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.member.domain.entity.MemberCategory;
import com.daejangjangi.backend.member.domain.entity.MemberDisease;
import com.daejangjangi.backend.member.exception.EmailDuplicationException;
import com.daejangjangi.backend.member.exception.NicknameDuplicationException;
import com.daejangjangi.backend.member.exception.NotFoundMemberException;
import com.daejangjangi.backend.member.repository.MemberCategoryRepository;
import com.daejangjangi.backend.member.repository.MemberDiseaseRepository;
import com.daejangjangi.backend.member.repository.MemberRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

  private final MemberRepository memberRepository;
  private final MemberDiseaseRepository memberDiseaseRepository;
  private final MemberCategoryRepository memberCategoryRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  /**
   * 2. 인증 전처리 - DB 회원 조회 후 UserDetails 반환
   *
   * @param username the username identifying the user whose data is required.
   * @return UserDetails
   * @throws UsernameNotFoundException
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Member member = findByEmail(username);
    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(member.getRole().name());
//    note : 여러 권한을 가질 경우 해당 로직 사용!
//    List<SimpleGrantedAuthority> authorities = member.getRoles().stream()
//        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
//        .collect(Collectors.toList());
    return new User(
        String.valueOf(member.getId()),
        member.getPassword(),
        true, true, true, true,
        Collections.singletonList(authority)
    );
  }

  /**
   * 이메일 중복 확인
   *
   * @param email
   */
  public void checkEmail(String email) {
    if (memberRepository.existsByEmail(email)) {
      throw new EmailDuplicationException();
    }
  }

  /**
   * 닉네임 중복 확인
   *
   * @param nickname
   */
  public void checkNickname(String nickname) {
    if (memberRepository.existsByNickname(nickname)) {
      throw new NicknameDuplicationException();
    }
  }

  /**
   * 회원가입
   *
   * @param member
   */
  @Transactional
  public void save(Member member, List<Disease> diseases, List<Category> categories) {
    String encodedPassword = passwordEncoder.encode(member.getPassword());
    member.encodePassword(encodedPassword);
    member = memberRepository.save(member);
    List<MemberDisease> memberDiseases = saveDiseases(member, diseases);
    List<MemberCategory> memberCategories = saveCategories(member, categories);
    member.addDiseases(memberDiseases);
    member.addCategories(memberCategories);
  }

  /**
   * 회원 정보 반환
   *
   * @return Member
   */
  public Member info() {
    Long id = getCurrentId();
    return findById(id);
  }

  /**
   * 로그인 회원 id 반환
   *
   * @return Long
   */
  public Long getCurrentId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (Objects.isNull(authentication)) {
      throw new RuntimeException();
    }
    String name = authentication.getName();
    return Long.parseLong(name);
  }

  /**
   * 회원 조회 by Id
   *
   * @param id
   * @return Member
   */
  public Member findById(Long id) {
    return memberRepository.findById(id).orElseThrow(NotFoundMemberException::new);
  }

  /**
   * 회원 조회 by email
   *
   * @param email
   * @return Member
   */
  public Member findByEmail(String email) {
    return memberRepository.findByEmail(email).orElseThrow(NotFoundMemberException::new);
  }

  /*--------------Private----------------------------Private----------------------------Private---*/

  /**
   * 회원 장건강 질환 저장
   *
   * @param member
   * @param diseases
   * @return List - MemberDisease
   */
  private List<MemberDisease> saveDiseases(Member member, List<Disease> diseases) {
    List<MemberDisease> memberDiseases = new ArrayList<>();
    for (Disease disease : diseases) {
      MemberDisease memberDisease = MemberDisease.builder()
          .member(member)
          .disease(disease)
          .build();
      memberDiseases.add(memberDisease);
    }
    return memberDiseaseRepository.saveAll(memberDiseases);
  }

  /**
   * 회원 관심 상품 카테고리 저장
   *
   * @param member
   * @param categories
   * @return List - MemberCategory
   */
  private List<MemberCategory> saveCategories(Member member, List<Category> categories) {
    List<MemberCategory> memberCategories = new ArrayList<>();
    for (Category category : categories) {
      MemberCategory memberCategory = MemberCategory.builder()
          .member(member)
          .category(category)
          .build();
      memberCategories.add(memberCategory);
    }
    return memberCategoryRepository.saveAll(memberCategories);
  }
}