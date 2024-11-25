package com.daejangjangi.backend.product.domain.mapper;

import com.daejangjangi.backend.global.common.PageFields;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.product.domain.dto.ProductRequestDto.Register;
import com.daejangjangi.backend.product.domain.dto.ProductResponseDto.ProductInfo;
import com.daejangjangi.backend.product.domain.dto.ProductResponseDto.ProductInfoList;
import com.daejangjangi.backend.product.domain.dto.ProductResponseDto.RecommendedProduct;
import com.daejangjangi.backend.product.domain.entity.Discount;
import com.daejangjangi.backend.product.domain.entity.Product;
import java.util.ArrayList;
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

//  @IterableMapping(elementTargetType = ProductInfo.class, qualifiedByName = "myProductToDto")
//  List<ProductInfo> bestProductToDto(List<Product> bestProduct);

  @Named("myProductToDto")
  @Mapping(target = "id", source = "product.id")
  @Mapping(target = "name", source = "product.name")
  @Mapping(target = "regularPrice", source = "product.regularPrice")
  @Mapping(target = "discountRate", expression = "java(initDiscountRate(product.getDiscount()))")
  @Mapping(target = "saleLink", source = "product.saleLink")
  @Mapping(target = "profile", source = "product.profile")
  @Mapping(target = "isLiked", expression = "java(isLikedByMember(product, member))")
  @Mapping(target = "tagList", expression = "java(getTagList(product))")
  ProductInfo ProductToInfoDto(Product product, Member member);

  default boolean isLikedByMember(Product product, Member member) {
    return product.getProductLikes().stream()
        .anyMatch(like -> like.getMember().equals(member));
  }

  default int initDiscountRate(Discount discount) {
    if (discount == null) {
      return 0;
    } else {
      return discount.getRate();
    }
  }

  default List<String> getTagList(Product product) {
    List<String> tagList = new ArrayList<>(product.getCategories().stream()
        .map(pc -> pc.getCategory().getName()).toList());
    tagList.addAll(product.getDiseases().stream()
        .map(pd -> pd.getDisease().getName()).toList());
    return tagList;
  }

  @Mapping(target = "pageFields", expression = "java(pageToDto(productInfos))")
  @Mapping(target = "myProductInfoList", source = "productInfos.content")
  ProductInfoList pageMyProductToDto(Page<ProductInfo> productInfos);

  @Mapping(target = "pageFields", expression = "java(pageToDto(productInfos))")
  @Mapping(target = "myProductInfoList", source = "productInfos.content")
  ProductInfoList pageSearchedProductToDto(Page<ProductInfo> productInfos);

  @Mapping(target = "pageNumber", expression = "java(getPageNumber(productInfos))")
  @Mapping(target = "pageSize", source = "productInfos.size")
  @Mapping(target = "totalElements", source = "productInfos.totalElements")
  @Mapping(target = "totalPages", source = "productInfos.totalPages")
  PageFields pageToDto(Page<ProductInfo> productInfos);

  default int getPageNumber(Page<ProductInfo> productInfos) {
    if (productInfos.getTotalElements() > 0) {
      return productInfos.getNumber() + 1;
    } else {
      return 0;
    }
  }
}
