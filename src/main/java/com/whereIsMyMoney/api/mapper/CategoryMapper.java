package com.whereIsMyMoney.api.mapper;

import com.whereIsMyMoney.api.model.CategoryDto;
import com.whereIsMyMoney.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    Category categoryDtoToCategory(CategoryDto categoryDto);
    CategoryDto categoryToCategoryDTto(Category category);
}
