package com.whereIsMyMoney.Controllers;

import com.whereIsMyMoney.api.model.ShopDto;
import com.whereIsMyMoney.service.ShopService;
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

public class ShopControllerTest {

    private static final Long ID = 1L;
    private static final String NAME = "shopName";

    @Mock
    ShopService shopService;

    ShopController shopController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        shopController = new ShopController(shopService);
        mockMvc = MockMvcBuilders.standaloneSetup(shopController).build();
    }

    @Test
    public void getAllShops() throws Exception {

        List<ShopDto> shopDtoList = Arrays.asList(new ShopDto(), new ShopDto());

        when(shopService.findAll()).thenReturn(shopDtoList);

        mockMvc.perform(get("/shops")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getShop() throws Exception {
        ShopDto shop = new ShopDto();
        shop.setName(NAME);

        when(shopService.findById(anyLong())).thenReturn(shop);

        mockMvc.perform(get("/shops/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)));
    }

    @Test
    public void addNew() throws Exception {
        ShopDto shop = new ShopDto();
        shop.setName(NAME);

        when(shopService.addNew(any(ShopDto.class))).thenReturn(shop);

        mockMvc.perform(post("/shops")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(shop)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(NAME)));
    }

    @Test
    public void updateShop() throws Exception {
        ShopDto shop = new ShopDto();
        shop.setName(NAME);

        when(shopService.update(any(ShopDto.class))).thenReturn(shop);

        mockMvc.perform(put("/shops/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(shop)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(NAME)));
    }

    @Test
    public void deleteShop() throws Exception {

        mockMvc.perform(delete("/shops/1"))
                .andExpect(status().isOk());

        verify(shopService).delete(anyLong());
    }
}