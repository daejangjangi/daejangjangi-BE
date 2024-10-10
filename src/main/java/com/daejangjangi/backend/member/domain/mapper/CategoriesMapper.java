package com.daejangjangi.backend.member.domain.mapper;

import com.daejangjangi.backend.member.domain.entity.MemberCategory;
import java.util.List;

public class CategoriesMapper {

  public List<String> asStringList(List<MemberCategory> diseases) {
    return diseases.stream().map(e -> e.getCategory().getName()).toList();
  }
}
