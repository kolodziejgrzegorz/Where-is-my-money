package com.whereIsMyMoney.service;

import com.whereIsMyMoney.api.mapper.BillMapper;
import com.whereIsMyMoney.api.mapper.BillMapperImpl;
import com.whereIsMyMoney.api.mapper.PurchaseMapper;
import com.whereIsMyMoney.api.model.BillDto;
import com.whereIsMyMoney.api.model.PurchaseDto;
import com.whereIsMyMoney.api.model.PurchaseListDto;
import com.whereIsMyMoney.dao.BillDao;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BillServiceTest {

    private static final Long ID = 1L;
    private static final Long USER_ID = 2L;
    private static final String SHOP_NAME = "shopName";

    @Mock
    BillDao dao;

    @Mock
    UserDao userDao;

    @Mock
    ShopDao shopDao;

    @Mock
    PurchaseMapper purchaseMapper;

    @InjectMocks
    BillMapper mapper = new BillMapperImpl();

    BillService service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        service = new BillService(dao, mapper);
    }

    @Test
    public void findAll() {
        List<Bill> bills = Arrays.asList(new Bill(), new Bill(), new Bill());

        when(dao.findAll()).thenReturn(bills);

        List<BillDto> billDtos =  service.findAll();

        assertEquals(3, billDtos.size());
    }

    @Test
    public void findById() {
        Bill bill = new Bill();
        bill.setId(ID);

        User user = new User();
        user.setId(USER_ID);

        Shop shop = new Shop();
        shop.setName(SHOP_NAME);

        List<Purchase> purchases = Arrays.asList(new Purchase(), new Purchase(), new Purchase());

        bill.setUser(user);
        bill.setShop(shop);
        bill.setPurchases(purchases);
        Optional<Bill> optionalBill = Optional.of(bill);

        when(dao.findById(anyLong())).thenReturn(optionalBill);

        BillDto dto = service.findById(ID);

        assertEquals(ID, dto.getId());
        assertEquals(USER_ID, dto.getUserId());
        assertEquals(SHOP_NAME, dto.getShopName());
        assertEquals(3, dto.getPurchaseListDto().getList().size());
    }

    @Test
    public void findByUserId() {
        User user = new User();
        user.setId(USER_ID);

        Bill bill1 = new Bill();
        bill1.setUser(user);
        Bill bill2 = new Bill();
        bill2.setUser(user);
        Bill bill3 = new Bill();
        bill3.setUser(user);


        List<Bill> bills = Arrays.asList(bill1, bill2, bill3);

        when(dao.findByUserId(anyLong())).thenReturn(bills);

        List<BillDto> billDtos = service.findByUserId(USER_ID);

        assertEquals(3, billDtos.size());
    }

    @Test
    public void addNew() {
        Bill bill = new Bill();
        bill.setId(ID);

        User user = new User();
        user.setId(USER_ID);

        Shop shop = new Shop();
        shop.setName(SHOP_NAME);

        List<Purchase> purchases = Arrays.asList(new Purchase(), new Purchase(), new Purchase());

        bill.setUser(user);
        bill.setShop(shop);
        bill.setPurchases(purchases);

        BillDto savedDto = new BillDto();
        savedDto.setUserId(USER_ID);
        savedDto.setShopName(SHOP_NAME);

        List<PurchaseDto> purchaseDtos = Arrays.asList(new PurchaseDto(), new PurchaseDto(), new PurchaseDto());
        PurchaseListDto objectList = new PurchaseListDto();
        objectList.setList(purchaseDtos);
        savedDto.setPurchaseListDto(objectList);

        when(dao.save(any(Bill.class))).thenReturn(bill);
        BillDto returnDto = service.addNew(savedDto);

        assertEquals(ID, returnDto.getId());
        assertEquals(USER_ID, returnDto.getUserId());
        assertEquals(SHOP_NAME, returnDto.getShopName());
        assertEquals(3, returnDto.getPurchaseListDto().getList().size());
    }

    @Test
    public void update() {
        Bill bill = new Bill();
        bill.setId(ID);

        User user = new User();
        user.setId(USER_ID);

        Shop shop = new Shop();
        shop.setName(SHOP_NAME);

        List<Purchase> purchases = Arrays.asList(new Purchase(), new Purchase(), new Purchase());

        bill.setUser(user);
        bill.setShop(shop);
        bill.setPurchases(purchases);

        BillDto savedDto = new BillDto();
        savedDto.setUserId(USER_ID);
        savedDto.setShopName(SHOP_NAME);

        List<PurchaseDto> purchaseDtos = Arrays.asList(new PurchaseDto(), new PurchaseDto(), new PurchaseDto());
        PurchaseListDto objectList = new PurchaseListDto();
        objectList.setList(purchaseDtos);
        savedDto.setPurchaseListDto(objectList);

        when(dao.save(any(Bill.class))).thenReturn(bill);

        BillDto returnDto = service.update(ID, savedDto);

        assertEquals(ID, returnDto.getId());
        assertEquals(USER_ID, returnDto.getUserId());
        assertEquals(SHOP_NAME, returnDto.getShopName());
        assertEquals(3, returnDto.getPurchaseListDto().getList().size());
    }

    @Test
    public void delete() {
        service.delete(ID);

        verify(dao).deleteById(anyLong());
    }

}