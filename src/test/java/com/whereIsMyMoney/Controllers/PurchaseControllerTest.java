package com.whereIsMyMoney.Controllers;

import com.whereIsMyMoney.api.model.PurchaseDto;
import com.whereIsMyMoney.service.PurchaseService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PurchaseControllerTest {

    private static final Long ID = 1L;
    private static final Long BILL_ID = 2L;


    @Mock
    PurchaseService service;

    PurchaseController controller;

    MockMvc mockMvc;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        controller = new PurchaseController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getAllPurchases() throws Exception {
        List<PurchaseDto> purchaseDtos = Arrays.asList(new PurchaseDto(), new PurchaseDto(), new PurchaseDto());

        when(service.findAll()).thenReturn(purchaseDtos);

        mockMvc.perform((get("/purchases")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));

    }

    @Test
    public void getBillPurchases() throws Exception {
        PurchaseDto purchaseDto1 = new PurchaseDto();
        PurchaseDto purchaseDto2 = new PurchaseDto();
        PurchaseDto purchaseDto3 = new PurchaseDto();
        purchaseDto1.setBill_id(BILL_ID);
        purchaseDto2.setBill_id(BILL_ID);
        purchaseDto3.setBill_id(BILL_ID);

        List<PurchaseDto> purchaseDtos = Arrays.asList(purchaseDto1, purchaseDto2, purchaseDto3);

        when(service.findByBillId(anyLong())).thenReturn(purchaseDtos);

        mockMvc.perform(get("/bills/2/purchases"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));

    }

    @Test
    public void getPurchase() throws Exception {

        PurchaseDto purchase = new PurchaseDto();
        purchase.setId(ID);

        when(service.findById(anyLong())).thenReturn(purchase);

        mockMvc.perform(get("/purchases/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID));

    }

}