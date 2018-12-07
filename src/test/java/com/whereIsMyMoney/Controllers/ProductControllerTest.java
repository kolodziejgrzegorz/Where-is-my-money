package com.whereIsMyMoney.Controllers;

import com.whereIsMyMoney.api.model.ProductDto;
import com.whereIsMyMoney.service.ProductService;
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

public class ProductControllerTest {

    private static final Long ID = 1L;
    private static final String NAME = "productName";

    @Mock
    ProductService productService;

    ProductController productController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        productController = new ProductController(productService);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void getAllProducts() throws Exception {

        List<ProductDto> productDtoList = Arrays.asList(new ProductDto(), new ProductDto());

        when(productService.findAll()).thenReturn(productDtoList);

        mockMvc.perform(get("/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getProduct() throws Exception {
        ProductDto product = new ProductDto();
        product.setName(NAME);

        when(productService.findById(anyLong())).thenReturn(product);

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)));
    }

    @Test
    public void addNew() throws Exception {
        ProductDto product = new ProductDto();
        product.setName(NAME);

        when(productService.addNew(any(ProductDto.class))).thenReturn(product);

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(NAME)));
    }

    @Test
    public void updateProduct() throws Exception {
        ProductDto product = new ProductDto();
        product.setName(NAME);

        when(productService.update(any(ProductDto.class))).thenReturn(product);

        mockMvc.perform(put("/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(NAME)));
    }

    @Test
    public void deleteProduct() throws Exception {

        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isOk());

        verify(productService).delete(anyLong());
    }

    @Test
    public void getByNameStartingWith() {
        //todo
    }
}