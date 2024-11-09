package com.daejangjangi.backend.product.domain.mapper;

import com.daejangjangi.backend.product.domain.dto.ProductResponseDto.RecommendedProduct;
import com.daejangjangi.backend.product.domain.entity.Product;
import com.daejangjangi.backend.product.domain.dto.ProductRequestDto.Register;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

  ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

  @Mapping(target = "name", source = "request.name")
  @Mapping(target = "comment", source = "request.comment")
  @Mapping(target = "saleLink", source = "request.saleLink")
  @Mapping(target = "regularPrice", source = "request.regularPrice")
  Product requestToEntity(Register request);

  @Mapping(target = "name", source = "product.name")
  @Mapping(target = "comment", source = "product.comment")
  @Mapping(target = "saleLink", source = "product.saleLink")
  @Mapping(target = "profile", source = "product.profile")
  RecommendedProduct entityToResponse(Product product);
}
