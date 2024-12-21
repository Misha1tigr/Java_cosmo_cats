package com.space_cats.marketplace.service.mappers;

import com.space_cats.marketplace.common.ProductAvailability;
import com.space_cats.marketplace.domain.Product;
import com.space_cats.marketplace.DTO.product.ProductResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "availability", source = "availability", qualifiedByName = "toDisplayName")
    ProductResponseDto toProductResponseDto(Product product);

    List<ProductResponseDto> toProductResponseDtoList(List<Product> productList);

    @Named("toDisplayName")
    default String toDisplayStatus(ProductAvailability availability) {
        return availability.getUserFriendlyStatus();
    }
}
