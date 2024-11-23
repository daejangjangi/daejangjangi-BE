package com.daejangjangi.backend.product.domain.mapper;

import com.daejangjangi.backend.product.domain.dto.DiscountRequestDto.DiscountRegister;
import com.daejangjangi.backend.product.domain.entity.Discount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DiscountMapper {

  DiscountMapper INSTANCE = Mappers.getMapper(DiscountMapper.class);


  @Mapping(target = "name", source = "request.name")
  @Mapping(target = "rate", source = "request.rate")
  Discount dtoToEntity(DiscountRegister request);
}
