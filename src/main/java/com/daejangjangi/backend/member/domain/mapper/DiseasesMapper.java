package com.daejangjangi.backend.member.domain.mapper;

import com.daejangjangi.backend.member.domain.entity.MemberDisease;
import java.util.List;

public class DiseasesMapper {

  public List<String> asStringList(List<MemberDisease> diseases) {
    return diseases.stream().map(e -> e.getDisease().getName()).toList();
  }
}
