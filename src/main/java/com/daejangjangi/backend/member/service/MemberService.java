package com.daejangjangi.backend.member.service;

import com.daejangjangi.backend.category.domain.Category;
import com.daejangjangi.backend.disease.domain.Disease;
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
   * @throws UsernameNotFoundException SecurityFilter 내 예외
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
   * @param email 이메일
   */
  public void checkEmail(String email) {
    if (memberRepository.existsByEmail(email)) {
      throw new EmailDuplicationException();
    }
  }

  /**
   * 닉네임 중복 확인
   *
   * @param nickname 닉네임
   */
  public void checkNickname(String nickname) {
    if (memberRepository.existsByNickname(nickname)) {
      throw new NicknameDuplicationException();
    }
  }

  /**
   * 회원가입
   *
   * @param member     회원 정보
   * @param diseases   장건강 질환
   * @param categories 관심 상품 카테고리
   */
  @Transactional
  public void save(Member member, List<Disease> diseases, List<Category> categories) {
    checkEmail(member.getEmail());
    checkNickname(member.getNickname());
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
   * @param id 회원 id
   * @return Member
   */
  public Member findById(Long id) {
    return memberRepository.findById(id).orElseThrow(NotFoundMemberException::new);
  }

  /**
   * 회원 조회 by email
   *
   * @param email 이메일
   * @return Member
   */
  public Member findByEmail(String email) {
    return memberRepository.findByEmail(email).orElseThrow(NotFoundMemberException::new);
  }

  /**
   * 회원 정보 수정
   *
   * @param newMember     수정된 회원 정보
   * @param newDiseases   수정된 장건강 질환
   * @param newCategories 수정된 관심 상품 카테고리
   */
  @Transactional
  public void update(Member newMember, List<Disease> newDiseases, List<Category> newCategories) {
    Long memberId = getCurrentId();
    Member originMember = findById(memberId);
    originMember.updateMember(newMember);
    List<MemberDisease> memberDiseases = updateDiseases(originMember, newDiseases);
    originMember.addDiseases(memberDiseases);
    List<MemberCategory> memberCategories = updateCategories(originMember, newCategories);
    originMember.addCategories(memberCategories);
  }

  /*--------------Private----------------------------Private----------------------------Private---*/

  /**
   * 회원 장건강 질환 수정
   *
   * @param originMember 기존 회원
   * @param newDiseases  새롭게 등록한 장건강 질환
   * @return List<MemberDisease>
   */
  private List<MemberDisease> updateDiseases(Member originMember, List<Disease> newDiseases) {
    List<Disease> removeDiseaseList = new ArrayList<>();
    List<Disease> addDiseaseList = new ArrayList<>();
    List<Disease> originDiseases = getOriginDiseases(originMember);
    // 새로운 회원 장건강 질환 목록에 포함되지 않는 기존 질환 항목 제거.
    for (Disease disease : originDiseases) {
      if (!newDiseases.contains(disease)) {
        removeDiseaseList.add(disease);
      }
    }
    removeDiseases(originMember, removeDiseaseList);
    // 기존 회원 장건강 질환 목록에 포함되지 않는 새로운 질환 항목 추가.
    for (Disease disease : newDiseases) {
      if (!originDiseases.contains(disease)) {
        addDiseaseList.add(disease);
      }
    }
    return saveDiseases(originMember, addDiseaseList);
  }

  /**
   * 회원 관심 상품 카테고리 수정
   *
   * @param originMember  기존 회원
   * @param newCategories 새롭게 등록한 관심 상품 카테고리
   * @return List<MemberCategory>
   */
  private List<MemberCategory> updateCategories(Member originMember, List<Category> newCategories) {
    List<Category> removeCategoryList = new ArrayList<>();
    List<Category> addCategoryList = new ArrayList<>();
    List<Category> originCategories = getOriginCategories(originMember);
    // 새로운 회원 관심 상품 카테고리 목록에 포함되지 않는 기존 관심 상품 항목 제거.
    for (Category category : originCategories) {
      if (!newCategories.contains(category)) {
        removeCategoryList.add(category);
      }
    }
    memberCategoryRepository.deleteAllByMemberAndCategories(originMember, removeCategoryList);
    // 기존 회원 관심 상품 카테고리 목록에 포함되지 않는 새로운 관심 상품 항목 추가.
    for (Category category : newCategories) {
      if (!originCategories.contains(category)) {
        addCategoryList.add(category);
      }
    }
    return saveCategories(originMember, addCategoryList);
  }

  /**
   * 회원 장건강 질환 저장
   *
   * @param member   회원 정보
   * @param diseases 질환
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
   * 회원 괌심 상품 카테고리 저장
   *
   * @param member     회원 정보
   * @param categories 카테고리
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

  /**
   * 회원 장건강 질환 제거
   *
   * @param member   회원 정보
   * @param diseases 장건강 질환
   */
  private void removeDiseases(Member member, List<Disease> diseases) {
    List<MemberDisease> memberDiseases =
        memberDiseaseRepository.findByMemberAndDiseaseIn(member, diseases);
    // todo : deleteAll & deleteAllInBatch 차이점 정리
    memberDiseaseRepository.deleteAllInBatch(memberDiseases);
  }

  /**
   * 기존 회원 장건강 질환 목록
   *
   * @param member 회원 정보
   * @return List<Disease>
   */
  private List<Disease> getOriginDiseases(Member member) {
    return member.getDiseases().stream()
        .map(MemberDisease::getDisease)
        .toList();
  }

  /**
   * 기존 회원 관심 상품 카테고리 목록
   *
   * @param member 회원 정보
   * @return List<Category>
   */
  private List<Category> getOriginCategories(Member member) {
    return member.getCategories().stream()
        .map(MemberCategory::getCategory)
        .toList();
  }
}