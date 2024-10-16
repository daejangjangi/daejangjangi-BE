package com.daejangjangi.backend.member;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.daejangjangi.backend.category.domain.Category;
import com.daejangjangi.backend.disease.domain.Disease;
import com.daejangjangi.backend.global.basic.ServiceTest;
import com.daejangjangi.backend.member.domain.dto.MemberRequestDto;
import com.daejangjangi.backend.member.domain.dto.MemberRequestDto.Join;
import com.daejangjangi.backend.member.domain.dto.MemberResponseDto;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.member.domain.entity.MemberCategory;
import com.daejangjangi.backend.member.domain.entity.MemberDisease;
import com.daejangjangi.backend.member.domain.mapper.MemberMapper;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class MemberMapperTest extends ServiceTest {

  @Test
  @DisplayName("MapStruct 동작 테스트")
  void request_to_entity_mapper_test() {
    // given
    String email = "email@test.com";
    String password = "test1234!@#$";
    String nickname = "nick";
    String gender = "w";
    LocalDate birth = LocalDate.now();

    MemberRequestDto.Join joinRequest =
        new Join(email, password, nickname, gender, birth, null, null);

    // when
    Member member = MemberMapper.INSTANCE.joinRequestToEntity(joinRequest);

    // then
    assertEquals(joinRequest.email(), member.getEmail());
    assertEquals(joinRequest.password(), member.getPassword());
    assertEquals(joinRequest.nickname(), member.getNickname());
    assertEquals(joinRequest.gender(), member.getGender());
    assertEquals(joinRequest.birth(), member.getBirth());
  }

  @Test
  @DisplayName("MapStruct 다른 타입 필드 매핑 테스트")
  void request_to_entity_mapper_test_another_type() {
    // given
    String nickname = "nick";
    String gender = "w";
    LocalDate birth = LocalDate.now();
    Disease disease = makeDisease(1L, "변비");
    Category category = makeCategory(1L, "식이섬유");
    Member member = Member.builder()
        .nickname(nickname)
        .birth(birth)
        .gender(gender)
        .build();
    List<MemberDisease> diseases = new ArrayList<>();
    diseases.add(MemberDisease.builder()
        .member(member)
        .disease(disease)
        .build());
    List<MemberCategory> categories = new ArrayList<>();
    categories.add(MemberCategory.builder()
        .member(member)
        .category(category)
        .build());
    member.addDiseases(diseases);
    member.addCategories(categories);
    List<String> strDiseases = diseases.stream().map(e -> e.getDisease().getName()).toList();
    List<String> strCategories = categories.stream().map(e -> e.getCategory().getName()).toList();

    // when
    MemberResponseDto.Info response = MemberMapper.INSTANCE.entityToInfoResponse(member);

    // then
    assertEquals(member.getNickname(), response.nickname());
    assertEquals(member.getGender(), response.gender());
    assertEquals(member.getBirth(), response.birth());
    assertEquals(strDiseases, response.diseases());
    assertEquals(strCategories, response.categories());
  }

  private Disease makeDisease(Long id, String name) {
    Disease disease = new Disease();
    ReflectionTestUtils.setField(disease, "id", id);
    ReflectionTestUtils.setField(disease, "name", name);
    return disease;
  }

  private Category makeCategory(Long id, String name) {
    Category category = new Category();
    ReflectionTestUtils.setField(category, "id", id);
    ReflectionTestUtils.setField(category, "name", name);
    return category;
  }
}
