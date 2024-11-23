package com.daejangjangi.backend.daejangtoon.domain.mapper;

import com.daejangjangi.backend.daejangtoon.domain.dto.DaejangtoonRequestDto;
import com.daejangjangi.backend.daejangtoon.domain.dto.DaejangtoonResponseDto;
import com.daejangjangi.backend.daejangtoon.domain.entity.Daejangtoon;
import com.daejangjangi.backend.daejangtoon.domain.entity.DaejangtoonChapter;
import com.daejangjangi.backend.daejangtoon.domain.entity.DaejangtoonImage;
import com.daejangjangi.backend.member.domain.entity.Member;
import java.util.Comparator;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DaejangtoonMapper {

  DaejangtoonMapper INSTANCE = Mappers.getMapper(DaejangtoonMapper.class);

  @Mapping(target = "title", source = "registerRequest.title")
  @Mapping(target = "yoil", source = "registerRequest.yoil")
  Daejangtoon registerToEntity(DaejangtoonRequestDto.Register registerRequest);

  @Mapping(target = "chapter", source = "registerRequest.chapter")
  @Mapping(target = "title", source = "registerRequest.title")
  DaejangtoonChapter registerChapterToEntity(DaejangtoonRequestDto.RegisterChapter registerRequest);

  default DaejangtoonResponseDto.Daejangtoon entityToDaejangtoonResponse(Daejangtoon daejangtoon,
      Member member) {
    return new DaejangtoonResponseDto.Daejangtoon(
        daejangtoon.getId(),
        daejangtoon.getTitle(),
        daejangtoon.getOverview(),
        daejangtoon.getYoil(),
        daejangtoon.getChapters().stream()
            .map(chapter -> entityToChaptersResponse(chapter, member))
            .toList()
    );
  }

  @Mapping(target = "id", source = "chapter.id")
  @Mapping(target = "chapter", source = "chapter.chapter")
  @Mapping(target = "title", source = "chapter.title")
  @Mapping(target = "profile", source = "chapter.profile")
  @Mapping(target = "hit", source = "chapter.hit")
  @Mapping(target = "likeCount", expression = "java(chapter.getToonLikes().size())")
  @Mapping(target = "isLiked", expression = "java(isLikedByMember(chapter, member))")
  DaejangtoonResponseDto.DaejangtoonChapters entityToChaptersResponse(DaejangtoonChapter chapter,
      Member member);

  @Mapping(target = "chapter", source = "chapter.chapter")
  @Mapping(target = "title", source = "chapter.title")
  @Mapping(target = "profile", source = "chapter.profile")
  @Mapping(target = "toonImages", expression = "java(sortToonImages(chapter))")
  @Mapping(target = "likeCount", expression = "java(chapter.getToonLikes().size())")
  @Mapping(target = "isLiked", expression = "java(isLikedByMember(chapter, member))")
  DaejangtoonResponseDto.DaejangtoonChapter entityToChapterResponse(DaejangtoonChapter chapter,
      Member member);

  default List<String> sortToonImages(DaejangtoonChapter chapter) {
    List<DaejangtoonImage> toonImages = chapter.getToonImages();
    toonImages.sort(Comparator.comparing(DaejangtoonImage::getOrder));
    return toonImages.stream().map(DaejangtoonImage::getImage).toList();
  }

  default boolean isLikedByMember(DaejangtoonChapter daejangtoonChapter, Member member) {
    return daejangtoonChapter.getToonLikes().stream()
        .anyMatch(like -> like.getMember().equals(member));
  }
}
