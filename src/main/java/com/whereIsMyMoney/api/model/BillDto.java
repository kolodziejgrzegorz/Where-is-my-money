package com.whereIsMyMoney.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class BillDto {

    private Long id;
    private LocalDate date;
    private String shopName;
    private Double sum;
    private Long userId;
    private PurchaseListDto purchaseListDto;

}
