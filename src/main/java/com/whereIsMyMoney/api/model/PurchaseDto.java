package com.whereIsMyMoney.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PurchaseDto {

    private Long id;
    private ProductDto productDto;
    private Integer productQuantity;
    private Double productPrice;
    private Double sum;
    private Long bill_id;

}
