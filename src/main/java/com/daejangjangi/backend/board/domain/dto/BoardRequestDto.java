package com.daejangjangi.backend.board.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public class BoardRequestDto {

  @Schema(name = "ModifyBoardRequest", description = "관심 게시판 수정 DTO")
  public record Modify(

      @Schema(description = "관심 게시판", allowableValues = {
          "변비", "과민성장증후군_설사형", "과민성장증후군_변비형", "치질", "치핵", "치열", "변실금", "항문소양증",
          "대장암", "크론병", "궤양성대장염", "복부팽만", "기타"
      })
      List<String> pinnedBoards
  ) {

  }

}
