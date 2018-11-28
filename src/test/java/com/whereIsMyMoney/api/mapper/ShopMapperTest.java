package com.whereIsMyMoney.api.mapper;

import com.whereIsMyMoney.api.model.ShopDto;
import com.whereIsMyMoney.domain.Shop;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShopMapperTest {

    private static final String NAME = "shopName";
    private static final Long ID = 1L;
    ShopMapper shopMapper = ShopMapper.INSTANCE;


    @Test
    public void shopDtoToShop() {
        ShopDto shopDto = new ShopDto();
        shopDto.setId(ID);
        shopDto.setName(NAME);

        Shop shop = shopMapper.shopDtoToShop(shopDto);

        assertEquals(NAME, shop.getName());
        assertEquals(ID, shop.getId());
    }

    @Test
    public void shopToShopDto() {
        Shop shop = new Shop();
        shop.setId(ID);
        shop.setName(NAME);

        ShopDto shopDto = shopMapper.shopToShopDto(shop);

        assertEquals(NAME, shopDto.getName());
        assertEquals(ID, shopDto.getId());
    }
}