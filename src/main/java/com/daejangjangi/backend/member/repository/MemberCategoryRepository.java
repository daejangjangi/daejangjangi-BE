package com.daejangjangi.backend.member.repository;

import com.daejangjangi.backend.member.domain.MemberCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCategoryRepository extends JpaRepository<MemberCategory, Long> {

}
