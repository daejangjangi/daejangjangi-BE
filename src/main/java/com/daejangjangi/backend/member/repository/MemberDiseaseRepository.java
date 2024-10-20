package com.daejangjangi.backend.member.repository;

import com.daejangjangi.backend.disease.domain.Disease;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.member.domain.entity.MemberDisease;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberDiseaseRepository extends JpaRepository<MemberDisease, Long> {

  List<MemberDisease> findByMemberAndDiseaseIn(Member member, List<Disease> diseases);
}
