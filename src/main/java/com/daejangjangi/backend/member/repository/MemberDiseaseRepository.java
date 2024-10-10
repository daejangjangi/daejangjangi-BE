package com.daejangjangi.backend.member.repository;

import com.daejangjangi.backend.member.domain.entity.MemberDisease;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberDiseaseRepository extends JpaRepository<MemberDisease, Long> {

}
