package com.daejangjangi.backend.product.domain.mapper;

import com.daejangjangi.backend.global.common.PageFields;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.product.domain.dto.ProductRequestDto.Register;
import com.daejangjangi.backend.product.domain.dto.ProductResponseDto.MyProductLikeList;
import com.daejangjangi.backend.product.domain.dto.ProductResponseDto.ProductInfo;
import com.daejangjangi.backend.product.domain.dto.ProductResponseDto.RecommendedProduct;
import com.daejangjangi.backend.product.domain.entity.Product;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper
public interface ProductMapper {

  ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

  @Mapping(target = "name", source = "request.name")
  @Mapping(target = "comment", source = "request.comment")
  @Mapping(target = "saleLink", source = "request.saleLink")
  @Mapping(target = "regularPrice", source = "request.regularPrice")
  Product requestToEntity(Register request);

  @IterableMapping(elementTargetType = RecommendedProduct.class, qualifiedByName = "recommendProductToDto")
  List<RecommendedProduct> recommendProductsToDtoList(List<Product> recommendedProduct);

  @Named("recommendProductToDto")
  @Mapping(target = "id", source = "product.id")
  @Mapping(target = "name", source = "product.name")
  @Mapping(target = "comment", source = "product.comment")
  @Mapping(target = "saleLink", source = "product.saleLink")
  @Mapping(target = "profile", source = "product.profile")
  RecommendedProduct recommendProductToDto(Product product);

  @Mapping(target = "id", source = "product.id")
  @Mapping(target = "name", source = "product.name")
  @Mapping(target = "regularPrice", source = "product.regularPrice")
  @Mapping(target = "discountRate", source = "product.discount.rate")
  @Mapping(target = "saleLink", source = "product.saleLink")
  @Mapping(target = "profile", source = "product.profile")
  @Mapping(target = "isLiked", expression = "java(isLikedByMember(product, member))")
  ProductInfo myProductToDto(Product product, Member member);

  default boolean isLikedByMember(Product product, Member member) {
    return product.getProductLikes().stream()
        .anyMatch(like -> like.getMember().equals(member));
  }

  @Mapping(target = "pageFields", expression = "java(pageToDto(myProductLikes))")
  @Mapping(target = "myProductLikeList", source = "myProductLikes.content")
  MyProductLikeList pageMyProductToDto(Page<ProductInfo> myProductLikes);

  @Mapping(target = "pageNumber", expression = "java(getPageNumber(myProductLikes))")
  @Mapping(target = "pageSize", source = "myProductLikes.size")
  @Mapping(target = "totalElements", source = "myProductLikes.totalElements")
  @Mapping(target = "totalPages", source = "myProductLikes.totalPages")
  PageFields pageToDto(Page<ProductInfo> myProductLikes);

  default int getPageNumber(Page<ProductInfo> myProductLikes) {
    if (myProductLikes.getTotalElements() > 0) {
      return myProductLikes.getNumber() + 1;
    } else {
      return 0;
    }
  }

  List<ProductInfo> bestProductToDto(List<Product> bestProduct);
}
