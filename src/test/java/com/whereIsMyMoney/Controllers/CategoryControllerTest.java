package com.whereIsMyMoney.Controllers;

import com.whereIsMyMoney.api.model.CategoryDto;
import com.whereIsMyMoney.service.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static com.whereIsMyMoney.Controllers.ObjectToJSON.convertToJson;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoryControllerTest {
    private static final Long ID = 1L;
    private static final String NAME = "categoryName";

    @Mock
    CategoryService categoryService;

    CategoryController categoryController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        categoryController = new CategoryController(categoryService);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    public void getAllCategories() throws Exception {

        List<CategoryDto> categoryDtoList = Arrays.asList(new CategoryDto(), new CategoryDto());

        when(categoryService.findAll()).thenReturn(categoryDtoList);

        mockMvc.perform(get("/categories")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getCategory() throws Exception {
        CategoryDto category = new CategoryDto();
        category.setId(ID);
        category.setName(NAME);

        when(categoryService.findById(anyLong())).thenReturn(category);

        mockMvc.perform(get("/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)));
    }

    @Test
    public void addNew() throws Exception {
        CategoryDto category = new CategoryDto();
        category.setName(NAME);

        when(categoryService.addNew(any(CategoryDto.class))).thenReturn(category);

        mockMvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(category)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(NAME)));
    }

    @Test
    public void updateCategory() throws Exception {
        CategoryDto category = new CategoryDto();
        category.setName(NAME);

        when(categoryService.update(any(CategoryDto.class))).thenReturn(category);

        mockMvc.perform(put("/categories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(category)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(NAME)));
    }

    @Test
    public void deleteCategory() throws Exception {

        mockMvc.perform(delete("/categories/1"))
                .andExpect(status().isOk());

        verify(categoryService).delete(anyLong());
    }
}