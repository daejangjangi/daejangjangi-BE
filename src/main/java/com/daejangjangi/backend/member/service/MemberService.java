package com.daejangjangi.backend.member.service;

import com.daejangjangi.backend.category.domain.entity.Category;
import com.daejangjangi.backend.disease.domain.entity.Disease;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.member.domain.entity.MemberCategory;
import com.daejangjangi.backend.member.domain.entity.MemberDisease;
import com.daejangjangi.backend.member.exception.EmailDuplicationException;
import com.daejangjangi.backend.member.exception.NicknameDuplicationException;
import com.daejangjangi.backend.member.repository.MemberCategoryRepository;
import com.daejangjangi.backend.member.repository.MemberDiseaseRepository;
import com.daejangjangi.backend.member.repository.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;
  private final MemberDiseaseRepository memberDiseaseRepository;
  private final MemberCategoryRepository memberCategoryRepository;
  private final BCryptPasswordEncoder passwordEncoder;

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
