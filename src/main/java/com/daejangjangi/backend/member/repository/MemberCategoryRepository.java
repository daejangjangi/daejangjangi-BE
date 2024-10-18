package com.daejangjangi.backend.member.repository;

import com.daejangjangi.backend.category.domain.Category;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.member.domain.entity.MemberCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberCategoryRepository extends JpaRepository<MemberCategory, Long> {

  @Modifying
  @Query("DELETE FROM MemberCategory mc WHERE mc.category IN :categories and mc.member = :member")
  void deleteAllByMemberAndCategories(@Param("member") Member member,
      @Param("categories") List<Category> categories);
}
