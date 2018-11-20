package com.whereIsMyMoney.api.mapper;

import com.whereIsMyMoney.api.model.BillDto;
import com.whereIsMyMoney.api.model.ShopDto;
import com.whereIsMyMoney.domain.Bill;
import com.whereIsMyMoney.domain.Shop;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class BillMapperTest {

    private final Long ID = 1L;
    private final LocalDate date = LocalDate.of(2018, 11, 11);
    private final String shopName = "nazwa";
    private final Double sum = 22.0;

    BillMapper billMapper = BillMapper.INSTANCE;

    @Test
    public void billToBillDto() {
        Shop shop = new Shop();
        shop.setName(shopName);

        Bill bill = new Bill();
        bill.setDate(date);
        bill.setId(ID);
        bill.setShop(shop);
        bill.setSum(sum);

        BillDto billDto = billMapper.billToBillDto(bill);

        assertEquals(shopName, billDto.getShop().getName());
        assertEquals(ID, billDto.getId());
        assertEquals(sum, billDto.getSum());
        assertEquals(date, billDto.getDate());
    }

    @Test
    public void billDtoToBill() {
        ShopDto shop = new ShopDto();
        shop.setName(shopName);

        BillDto billDto = new BillDto();
        billDto.setDate(date);
        billDto.setId(ID);
        billDto.setShop(shop);
        billDto.setSum(sum);

        Bill bill = billMapper.billDtoToBill(billDto);

        assertEquals(shopName, bill.getShop().getName());
        assertEquals(ID, bill.getId());
        assertEquals(sum, bill.getSum());
        assertEquals(date, bill.getDate());
    }
}