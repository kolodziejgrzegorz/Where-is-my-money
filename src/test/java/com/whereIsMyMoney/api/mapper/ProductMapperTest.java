package com.whereIsMyMoney.api.mapper;

import com.whereIsMyMoney.api.model.ProductDto;
import com.whereIsMyMoney.dao.CategoryDao;
import com.whereIsMyMoney.domain.Category;
import com.whereIsMyMoney.domain.Product;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ProductMapperTest {

    private static final String NAME = "productName";
    private static final String CATEGORY_NAME = "categoryName";
    private static final Long ID = 1L;

    @Mock
    CategoryDao categoryDao;

    @InjectMocks
    ProductMapper productMapper = new ProductMapperImpl();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void productDtoToProduct() {
        ProductDto productDto = new ProductDto();
        productDto.setId(ID);
        productDto.setName(NAME);
        productDto.setCategoryName(CATEGORY_NAME);

        Category category = new Category();
        category.setName(CATEGORY_NAME);
        Optional<Category> optionalCategory = Optional.of(category);

        when(categoryDao.findByName(CATEGORY_NAME)).thenReturn(optionalCategory);

        Product product = productMapper.productDtoToProduct(productDto);
        assertEquals(ID, product.getId());
        assertEquals(NAME, product.getName());
        assertEquals(CATEGORY_NAME, product.getCategory().getName());
    }

    @Test
    public void productToProductDto() {
        Product product = new Product();
        product.setId(ID);
        product.setName(NAME);

        Category category = new Category();
        category.setName(CATEGORY_NAME);

        product.setCategory(category);

        ProductDto productDto = productMapper.productToProductDto(product);

        assertEquals(ID, productDto.getId());
        assertEquals(NAME, productDto.getName());
        assertEquals(CATEGORY_NAME, productDto.getCategoryName());

    }

    @Test
    public void categoryNameToCategory() {

        Category c = new Category();
        c.setName(CATEGORY_NAME);
        c.setId(ID);
        Optional<Category> category = Optional.of(c);

        when(categoryDao.findByName(any())).thenReturn(category);

        Category returnCategory = productMapper.categoryNameToCategory(CATEGORY_NAME);

        assertEquals(CATEGORY_NAME, returnCategory.getName());
        assertEquals(ID, returnCategory.getId());
    }
}