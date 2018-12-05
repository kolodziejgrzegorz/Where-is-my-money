package com.whereIsMyMoney.service;

import com.whereIsMyMoney.api.mapper.ShopMapper;
import com.whereIsMyMoney.api.model.ShopDto;
import com.whereIsMyMoney.dao.ShopDao;
import com.whereIsMyMoney.domain.Shop;
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

public class ShopServiceTest {

    private static final Long ID = 1L;
    private static final String NAME = "shopName";

    @Mock
    ShopDao dao;

    ShopService service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        service = new ShopService(dao, ShopMapper.INSTANCE);
    }

    @Test
    public void findAll() {
        List<Shop> shopList = Arrays.asList(new Shop(), new Shop(), new Shop());

        when(dao.findAll()).thenReturn(shopList);

        List<ShopDto> shopDtos = service.findAll();

        assertEquals(3, shopDtos.size());
    }

    @Test
    public void findById() {
        Shop shop = new Shop();
        shop.setId(ID);
        Optional<Shop> optional = Optional.of(shop);

        when(dao.findById(anyLong())).thenReturn(optional);
        ShopDto dto = service.findById(ID);

        assertEquals(ID, dto.getId());
    }

    @Test
    public void findByName() {
        Shop shop = new Shop();
        shop.setName(NAME);
        Optional<Shop> optional = Optional.of(shop);

        when(dao.findByName(anyString())).thenReturn(optional);
        ShopDto dto = service.findByName(NAME);

        assertEquals(NAME, dto.getName());
    }

    @Test
    public void addNewOrUpdate() {
        Shop shop = new Shop();
        shop.setId(ID);
        shop.setName(NAME);

        ShopDto dto = new ShopDto();
        dto.setName(NAME);

        when(dao.save(any(Shop.class))).thenReturn(shop);

        ShopDto saveDto = service.addNew(dto);

        assertEquals(ID, saveDto.getId());
        assertEquals(NAME, saveDto.getName());
    }


    @Test
    public void delete() {

        service.delete(ID);

        verify(dao).deleteById(anyLong());
    }
}