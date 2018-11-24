package com.whereIsMyMoney.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PurchaseListDto {
    private List<PurchaseDto> list;
}
