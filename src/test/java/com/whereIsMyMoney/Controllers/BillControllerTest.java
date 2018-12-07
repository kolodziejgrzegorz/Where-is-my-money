package com.whereIsMyMoney.Controllers;

import com.whereIsMyMoney.api.model.BillDto;
import com.whereIsMyMoney.service.BillService;
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

public class BillControllerTest {

    private static final Long ID = 1L;
    private static final Long USER_ID = 2L;
    private static final String SHOP_NAME = "shopName";
    

    @Mock
    BillService billService;

    BillController billController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        billController = new BillController(billService);
        mockMvc = MockMvcBuilders.standaloneSetup(billController).build();
    }

    @Test
    public void getAllBills() throws Exception {

        List<BillDto> billDtoList = Arrays.asList(new BillDto(), new BillDto());

        when(billService.findAll()).thenReturn(billDtoList);

        mockMvc.perform(get("/bills")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getBill() throws Exception {
        BillDto bill = new BillDto();
        bill.setShopName(SHOP_NAME);
        bill.setUserId(USER_ID);

        when(billService.findById(anyLong())).thenReturn(bill);

        mockMvc.perform(get("/bills/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shopName", equalTo(SHOP_NAME)))
                .andExpect(jsonPath("$.userId").value(USER_ID));
    }

    @Test
    public void addNew() throws Exception {
        BillDto bill = new BillDto();
        bill.setShopName(SHOP_NAME);
        bill.setUserId(USER_ID);

        when(billService.addNew(any(BillDto.class))).thenReturn(bill);

        mockMvc.perform(post("/bills")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(bill)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.shopName", equalTo(SHOP_NAME)))
                .andExpect(jsonPath("$.userId").value(USER_ID));
    }

    @Test
    public void updateBill() throws Exception {
        BillDto bill = new BillDto();
        bill.setShopName(SHOP_NAME);
        bill.setUserId(USER_ID);
        bill.setId(ID);

        when(billService.update(anyLong(), any(BillDto.class))).thenReturn(bill);

        mockMvc.perform(put("/bills/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(bill)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.shopName", equalTo(SHOP_NAME)))
                .andExpect(jsonPath("$.userId").value(USER_ID));
    }

    @Test
    public void deleteBill() throws Exception {

        mockMvc.perform(delete("/bills/1"))
                .andExpect(status().isOk());

        verify(billService).delete(anyLong());
    }

}