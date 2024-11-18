package com.daejangjangi.backend.product.domain.mapper;

import com.daejangjangi.backend.product.domain.dto.ProductRequestDto.Register;
import com.daejangjangi.backend.product.domain.dto.ProductResponseDto.RecommendedProduct;
import com.daejangjangi.backend.product.domain.entity.Product;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

  ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

  @Mapping(target = "name", source = "request.name")
  @Mapping(target = "comment", source = "request.comment")
  @Mapping(target = "saleLink", source = "request.saleLink")
  @Mapping(target = "regularPrice", source = "request.regularPrice")
  Product requestToEntity(Register request);

  @IterableMapping(elementTargetType = RecommendedProduct.class, qualifiedByName = "entityToResponse")
  List<RecommendedProduct> entitiesToResponses(List<Product> recommendedProduct);

  @Named("entityToResponse")
  @Mapping(target = "id", source = "product.id")
  @Mapping(target = "name", source = "product.name")
  @Mapping(target = "comment", source = "product.comment")
  @Mapping(target = "saleLink", source = "product.saleLink")
  @Mapping(target = "profile", source = "product.profile")
  RecommendedProduct entityToResponse(Product product);
}
