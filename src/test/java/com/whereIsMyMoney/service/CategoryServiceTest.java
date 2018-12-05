package com.whereIsMyMoney.service;

import com.whereIsMyMoney.api.mapper.CategoryMapper;
import com.whereIsMyMoney.api.model.CategoryDto;
import com.whereIsMyMoney.dao.CategoryDao;
import com.whereIsMyMoney.domain.Category;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CategoryServiceTest {

    private static final Long ID = 1L;
    private static final String NAME = "categoryName";

    @Mock
    CategoryDao dao;

    CategoryService service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        service = new CategoryService(dao, CategoryMapper.INSTANCE);
    }

    @Test
    public void findAll() {
        List<Category> categoryList = Arrays.asList(new Category(), new Category(), new Category());

        when(dao.findAll()).thenReturn(categoryList);

        List<CategoryDto> categoryDtos = service.findAll();

        assertEquals(3, categoryDtos.size());
    }

    @Test
    public void findById() {
        Category category = new Category();
        category.setId(ID);
        Optional<Category> optional = Optional.of(category);

        when(dao.findById(anyLong())).thenReturn(optional);
        CategoryDto dto = service.findById(ID);

        assertEquals(ID, dto.getId());
    }

    @Test
    public void findByName() {
        Category category = new Category();
        category.setName(NAME);
        Optional<Category> optional = Optional.of(category);

        when(dao.findByName(anyString())).thenReturn(optional);
        CategoryDto dto = service.findByName(NAME);

        assertEquals(NAME, dto.getName());
    }

    @Test
    public void addNewOrUpdate() {
        Category category = new Category();
        category.setId(ID);
        category.setName(NAME);

        CategoryDto dto = new CategoryDto();
        dto.setName(NAME);

        when(dao.save(any(Category.class))).thenReturn(category);

        CategoryDto saveDto = service.addNew(dto);

        assertEquals(ID, saveDto.getId());
        assertEquals(NAME, saveDto.getName());
    }


    @Test
    public void delete() {

        service.delete(ID);

        verify(dao).deleteById(anyLong());
    }
}