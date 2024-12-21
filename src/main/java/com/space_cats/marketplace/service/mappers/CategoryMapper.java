package com.space_cats.marketplace.service.mappers;

import com.space_cats.marketplace.domain.Category;
import com.space_cats.marketplace.DTO.category.CategoryDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto categoryToCategoryDto(Category category);
    List<CategoryDto> categoryListToCategoryDtoList(List<Category> categoryList);
}
