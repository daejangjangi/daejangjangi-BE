package com.daejangjangi.backend.banner.domain.mapper;

import com.daejangjangi.backend.banner.domain.dto.BannerResponseDto.BannerInfo;
import com.daejangjangi.backend.banner.domain.entity.Banner;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BannerMapper {

  BannerMapper INSTANCE = Mappers.getMapper(BannerMapper.class);

  @Named("entityToResponse")
  @Mapping(target = "id", source = "banner.id")
  @Mapping(target = "bannerImage", source = "banner.image")
  @Mapping(target = "saleLink", source = "banner.product.saleLink")
  BannerInfo entityToResponse(Banner banner);

  @IterableMapping(elementTargetType = BannerInfo.class, qualifiedByName = "entityToResponse")
  List<BannerInfo> entityToResponse(List<Banner> bannerList);
}
