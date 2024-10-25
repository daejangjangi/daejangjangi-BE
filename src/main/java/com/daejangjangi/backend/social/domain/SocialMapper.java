package com.daejangjangi.backend.social.domain;

import com.daejangjangi.backend.social.domain.dto.SocialResponseDto;
import com.daejangjangi.backend.token.domain.dto.TokenResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SocialMapper {

  SocialMapper INSTANCE = Mappers.getMapper(SocialMapper.class);

  @Mapping(target = "accessToken", source = "tokenDto.accessToken")
  @Mapping(target = "refreshToken", source = "tokenDto.refreshToken")
  SocialResponseDto.SocialLogin dtoToResponse(TokenResponseDto tokenDto);
}
