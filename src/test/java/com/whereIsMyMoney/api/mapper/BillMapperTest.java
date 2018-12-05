package com.whereIsMyMoney.api.mapper;

import com.whereIsMyMoney.api.model.BillDto;
import com.whereIsMyMoney.api.model.PurchaseDto;
import com.whereIsMyMoney.api.model.PurchaseListDto;
import com.whereIsMyMoney.api.model.ShopDto;
import com.whereIsMyMoney.dao.ShopDao;
import com.whereIsMyMoney.dao.UserDao;
import com.whereIsMyMoney.domain.Bill;
import com.whereIsMyMoney.domain.Purchase;
import com.whereIsMyMoney.domain.Shop;
import com.whereIsMyMoney.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class BillMapperTest {

    private static final Long ID = 1L;
    private static final Long USER_ID = 2L;
    private static final LocalDate DATE = LocalDate.of(2018, 11, 11);
    private static final String SHOP_NAME = "shopName";
    private static final Double SUM = 22.0;

    @Mock
    ShopDao shopDao;

    @Mock
    UserDao userDao;

    @Mock
    PurchaseMapper purchaseMapper;

    @InjectMocks
    BillMapper billMapper = new BillMapperImpl();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void billToBillDto() {
        Shop shop = new Shop();
        shop.setName(SHOP_NAME);
        User user = new User();
        user.setId(USER_ID);

        Bill bill = new Bill();
        bill.setDate(DATE);
        bill.setId(ID);
        bill.setShop(shop);
        bill.setSum(SUM);
        bill.setUser(user);

        List<Purchase> purchases = Arrays.asList(new Purchase(), new Purchase());
        bill.setPurchases(purchases);

        BillDto billDto = billMapper.billToBillDto(bill);

        assertEquals(SHOP_NAME, billDto.getShopName());
        assertEquals(SUM, billDto.getSum());
        assertEquals(DATE, billDto.getDate());
        assertEquals(USER_ID, billDto.getUserId());
        assertEquals(2,billDto.getPurchaseListDto().getList().size());

    }

    @Test
    public void billDtoToBill() {
        ShopDto shopDto = new ShopDto();
        shopDto.setName(SHOP_NAME);

        Shop shop = new Shop();
        shop.setName(SHOP_NAME);
        Optional<Shop> optionalShop = Optional.of(shop);

        User user = new User();
        user.setId(USER_ID);

        BillDto billDto = new BillDto();
        billDto.setDate(DATE);
        billDto.setShopName(SHOP_NAME);
        billDto.setSum(SUM);
        billDto.setUserId(USER_ID);

        List<PurchaseDto> purchaseDtos = Arrays.asList(new PurchaseDto(), new PurchaseDto());
        PurchaseListDto purchaseListDto = new PurchaseListDto();
        purchaseListDto.setList(purchaseDtos);
        billDto.setPurchaseListDto(purchaseListDto);

        when(userDao.getOne(anyLong())).thenReturn(user);
        when(shopDao.findByName(anyString())).thenReturn(optionalShop);
        Bill bill = billMapper.billDtoToBill(billDto);

        assertEquals(SHOP_NAME, bill.getShop().getName());
        assertEquals(USER_ID, bill.getUser().getId());
        assertEquals(SUM, bill.getSum());
        assertEquals(DATE, bill.getDate());
        assertEquals(2, bill.getPurchases().size());
    }

    @Test
    public void shopNameToShop() {
        Shop s = new Shop();
        s.setId(ID);
        s.setName(SHOP_NAME);

        Optional<Shop> shopOptional = Optional.of(s);

        when(shopDao.findByName(any())).thenReturn(shopOptional);

        Shop returnShop = billMapper.shopNameToShop(SHOP_NAME);

        assertEquals(ID, returnShop.getId());
        assertEquals(SHOP_NAME, returnShop.getName());
    }
}