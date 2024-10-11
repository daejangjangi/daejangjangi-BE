package com.daejangjangi.backend.member.domain.mapper;

import com.daejangjangi.backend.member.domain.dto.MemberRequestDto;
import com.daejangjangi.backend.member.domain.dto.MemberResponseDto;
import com.daejangjangi.backend.member.domain.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {DiseasesMapper.class, CategoriesMapper.class})
public interface MemberMapper {

  MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

  @Mapping(target = "email", source = "joinRequest.email")
  @Mapping(target = "password", source = "joinRequest.password")
  @Mapping(target = "nickname", source = "joinRequest.nickname")
  @Mapping(target = "gender", source = "joinRequest.gender")
  @Mapping(target = "birth", source = "joinRequest.birth")
  Member joinRequestToEntity(MemberRequestDto.Join joinRequest);

  @Mapping(target = "birth", source = "member.birth")
  @Mapping(target = "gender", source = "member.gender")
  @Mapping(target = "nickname", source = "member.nickname")
  @Mapping(target = "diseases", source = "member.diseases")
  @Mapping(target = "categories", source = "member.categories")
  MemberResponseDto.Info entityToInfoResponse(Member member);
}
