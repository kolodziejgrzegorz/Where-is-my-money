package com.whereIsMyMoney.api.mapper;

import com.whereIsMyMoney.api.model.ProductDto;
import com.whereIsMyMoney.api.model.PurchaseDto;
import com.whereIsMyMoney.dao.BillDao;
import com.whereIsMyMoney.domain.Bill;
import com.whereIsMyMoney.domain.Product;
import com.whereIsMyMoney.domain.Purchase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class PurchaseMapperTest {

    private static final Long ID = 1L;
    private static final Long BILL_ID = 2L;
    private static final Long PRODUCT_ID = 3L;
    private static final Double SUM = 1.0;
    private static final String NAME = "productName";
    private static final String C_NAME = "categoryName";


    @Mock
    BillDao billDao;

    @Mock
    ProductMapper productMapper;

    @InjectMocks
    PurchaseMapper purchaseMapper = PurchaseMapper.INSTANCE;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void purchaseDtoToPurchase() {
        Bill bill = new Bill();
        bill.setId(BILL_ID);

        PurchaseDto purchaseDto = new PurchaseDto();
        purchaseDto.setBill_id(BILL_ID);
        purchaseDto.setId(ID);
        purchaseDto.setSum(SUM);

        ProductDto productDto = new ProductDto();

        purchaseDto.setProductDto(productDto);

        Product product = new Product();

        when(billDao.getOne(anyLong())).thenReturn(bill);
        when(productMapper.productDtoToProduct(any())).thenReturn(product);

        Purchase purchase = purchaseMapper.purchaseDtoToPurchase(purchaseDto);

        assertEquals(BILL_ID, purchase.getBill().getId());
        assertEquals(ID, purchase.getId());
        assertEquals(SUM, purchase.getSum());
        assertNotNull(purchase.getProduct());
    }

    @Test
    public void purchaseToPurchaseDto() {
        Bill bill = new Bill();
        bill.setId(BILL_ID);

        Purchase purchase = new Purchase();
        purchase.setBill(bill);
        purchase.setId(ID);
        purchase.setSum(SUM);

        Product product = new Product();
        ProductDto productDto = new ProductDto();
        purchase.setProduct(product);

        when(productMapper.productToProductDto(any())).thenReturn(productDto);

        PurchaseDto purchaseDto = purchaseMapper.purchaseToPurchaseDto(purchase);

        assertEquals(BILL_ID, purchaseDto.getBill_id());
        assertEquals(ID, purchaseDto.getId());
        assertEquals(SUM, purchaseDto.getSum());
        assertNotNull(purchaseDto.getProductDto());
    }
}