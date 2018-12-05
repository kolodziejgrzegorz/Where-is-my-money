package com.whereIsMyMoney.service;

import com.whereIsMyMoney.api.mapper.ProductMapper;
import com.whereIsMyMoney.api.mapper.ProductMapperImpl;
import com.whereIsMyMoney.api.model.ProductDto;
import com.whereIsMyMoney.dao.CategoryDao;
import com.whereIsMyMoney.dao.ProductDao;
import com.whereIsMyMoney.domain.Category;
import com.whereIsMyMoney.domain.Product;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductServiceTest {


    private static final Long ID = 1L;
    private static final String NAME = "productName";
    private static final String CATEGORY_NAME = "categoryName";


    @Mock
    ProductDao dao;

    @Mock
    CategoryDao categoryDao;

    @InjectMocks
    ProductMapper mapper = new ProductMapperImpl();

    ProductService service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        service = new ProductService(dao, mapper);
    }

    @Test
    public void findAll() {
        List<Product> productList = Arrays.asList(new Product(), new Product(), new Product());

        when(dao.findAll()).thenReturn(productList);

        List<ProductDto> productDtos = service.findAll();

        assertEquals(3, productDtos.size());
    }

    @Test
    public void findById() {
        Product product = new Product();
        product.setId(ID);

        Category c = new Category();
        c.setId(2L);
        c.setName(CATEGORY_NAME);
        product.setCategory(c);
        Optional<Product> optional = Optional.of(product);

        when(dao.findById(anyLong())).thenReturn(optional);
        ProductDto dto = service.findById(ID);

        assertEquals(ID, dto.getId());
        assertEquals(CATEGORY_NAME, dto.getCategoryName());
    }

    @Test
    public void findByName() {
        Product product = new Product();
        product.setName(NAME);

        Category c = new Category();
        c.setName(CATEGORY_NAME);
        product.setCategory(c);
        Optional<Product> optional = Optional.of(product);

        ProductDto productDto = new ProductDto();
        productDto.setName(NAME);
        productDto.setCategoryName(CATEGORY_NAME);


        when(dao.findByName(anyString())).thenReturn(optional);
        ProductDto dto = service.findByName(NAME);

        assertEquals(NAME, dto.getName());
        assertEquals(CATEGORY_NAME, dto.getCategoryName());
    }

    @Test
    public void addNew() {
        Product product = new Product();
        product.setId(ID);
        product.setName(NAME);

        ProductDto dto = new ProductDto();
        dto.setName(NAME);

        Optional<Product> optionalProduct = Optional.empty();

        when(dao.findByName(anyString())).thenReturn(optionalProduct);
        when(dao.save(any(Product.class))).thenReturn(product);
        ProductDto saveDto = service.addNew(dto);

        assertEquals(ID, saveDto.getId());
        assertEquals(NAME, saveDto.getName());
    }

    @Test
    public void update() {
        Product product = new Product();
        product.setId(ID);
        product.setName(NAME);

        Optional<Product> optionalProduct = Optional.of(product);


        ProductDto dto = new ProductDto();
        dto.setName(NAME);
        dto.setId(ID);

        Optional<Product> optionalEmptyProduct = Optional.empty();

        when(dao.findById(anyLong())).thenReturn(optionalProduct);
        when(dao.findByName(anyString())).thenReturn(optionalEmptyProduct);
        when(dao.save(any(Product.class))).thenReturn(product);
        ProductDto saveDto = service.update(dto);

        assertEquals(ID, saveDto.getId());
        assertEquals(NAME, saveDto.getName());

    }

    @Test
    public void delete() {

        service.delete(ID);

        verify(dao).deleteById(anyLong());
    }

    @Test
    public void getByNameStartingWith() {
        //todo
    }

}