package com.daejangjangi.backend.board.domain.mapper;

import com.daejangjangi.backend.board.domain.dto.BoardResponseDto;
import com.daejangjangi.backend.member.domain.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {BoardMapper.class})
public interface MemberBoardMapper {

  MemberBoardMapper INSTANCE = Mappers.getMapper(MemberBoardMapper.class);

  @Mapping(target = "pinnedBoards", source = "member.pinnedBoards")
  BoardResponseDto.Info entityToInfoResponse(Member member);

}
