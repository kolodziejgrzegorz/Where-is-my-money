package com.whereIsMyMoney.service;

import com.whereIsMyMoney.api.mapper.ProductMapper;
import com.whereIsMyMoney.api.mapper.ProductMapperImpl;
import com.whereIsMyMoney.api.mapper.PurchaseMapper;
import com.whereIsMyMoney.api.model.ProductDto;
import com.whereIsMyMoney.api.model.PurchaseDto;
import com.whereIsMyMoney.dao.BillDao;
import com.whereIsMyMoney.dao.PurchaseDao;
import com.whereIsMyMoney.domain.Bill;
import com.whereIsMyMoney.domain.Product;
import com.whereIsMyMoney.domain.Purchase;
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

public class PurchaseServiceTest {

    private static final Long ID = 1L;
    private static final Long BILL_ID = 2L;
    private static final Long PRODUCT_ID = 2L;
    private static final String NAME = "productName";

    @Mock
    PurchaseDao dao;

    @Mock
    ProductMapper productMapper = new ProductMapperImpl();;

    @Mock
    BillDao billDao;

    @InjectMocks
    PurchaseMapper mapper = PurchaseMapper.INSTANCE;

    PurchaseService service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        service = new PurchaseService(dao, mapper);
    }

    @Test
    public void findAll() {

        List<Purchase> productList = Arrays.asList(new Purchase(), new Purchase(), new Purchase());

        when(dao.findAll()).thenReturn(productList);

        List<PurchaseDto> purchaseDtos = service.findAll();

        assertEquals(3, purchaseDtos.size());
    }

    @Test
    public void findById() {
        Purchase purchase = new Purchase();
        purchase.setId(ID);
        Optional<Purchase> optionalPurchase = Optional.of(purchase);

        Product product = new Product();
        product.setName(NAME);
        purchase.setProduct(product);

        ProductDto productDto = new ProductDto();
        productDto.setName(NAME);

        when(productMapper.productToProductDto(any(Product.class))).thenReturn(productDto);
        when(dao.findById(anyLong())).thenReturn(optionalPurchase);

        PurchaseDto dto = service.findById(ID);

        assertEquals(ID, dto.getId());
        assertEquals(NAME, dto.getProductDto().getName());
    }

    @Test
    public void findByBillId() {
        Bill bill = new Bill();
        bill.setId(BILL_ID);

        Purchase purchase = new Purchase();
        purchase.setBill(bill);
        Purchase purchase1 = new Purchase();
        purchase1.setBill(bill);

        List<Purchase> purchases = Arrays.asList(purchase, purchase1);

        when(dao.findByBillId(anyLong())).thenReturn(purchases);

        List<PurchaseDto> purchaseDtos = service.findByBillId(BILL_ID);

        assertEquals(2,purchaseDtos.size());
    }

    @Test
    public void findByProductId() {
        Product product = new Product();
        product.setId(PRODUCT_ID);

        Purchase purchase = new Purchase();
        purchase.setProduct(product);
        Purchase purchase1 = new Purchase();
        purchase1.setProduct(product);

        List<Purchase> purchases = Arrays.asList(purchase, purchase1);

        when(dao.findByProductId(anyLong())).thenReturn(purchases);

        List<PurchaseDto> purchaseDtos = service.findByProductId(PRODUCT_ID);

        assertEquals(2,purchaseDtos.size());
    }

    @Test
    public void delete() {
        service.delete(ID);

        verify(dao).deleteById(ID);
    }
}