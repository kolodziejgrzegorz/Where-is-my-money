package com.whereIsMyMoney.api.mapper;

import com.whereIsMyMoney.api.model.CategoryDto;
import com.whereIsMyMoney.domain.Category;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CategoryMapperTest {


    private static final String NAME = "categoryName";
    private static final Long ID = 1L;
    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;


    @Test
    public void categoryDtoToCategory() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(ID);
        categoryDto.setName(NAME);

        Category category = categoryMapper.categoryDtoToCategory(categoryDto);

        assertEquals(NAME, category.getName());
        assertEquals(ID, category.getId());
    }

    @Test
    public void categoryToCategoryDto() {
        Category category = new Category();
        category.setId(ID);
        category.setName(NAME);

        CategoryDto categoryDto = categoryMapper.categoryToCategoryDto(category);

        assertEquals(NAME, categoryDto.getName());
        assertEquals(ID, categoryDto.getId());
    }
}