package com.whereIsMyMoney.api.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BillDto {

    private Long id;
    private LocalDate date;
    private String shopName;
    private Double sum;
    private Long userId;
    private PurchaseListDto purchaseListDto;

}
