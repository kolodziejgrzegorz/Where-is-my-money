package com.whereIsMyMoney.api.mapper;

import com.whereIsMyMoney.api.model.PurchaseDto;
import com.whereIsMyMoney.domain.Purchase;
import com.whereIsMyMoney.service.BillService;
import com.whereIsMyMoney.service.ProductService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {BillService.class, ProductService.class})
public interface PurchaseMapper {

    PurchaseMapper INSTANCE = Mappers.getMapper(PurchaseMapper.class);

    @Mappings({
            @Mapping(source = "bill_id" , target = "bill"),
            @Mapping(source = "product_id" , target = "product")
    })
    Purchase purchaseDtoToPurchase(PurchaseDto purchaseDto);

    @Mappings({
            @Mapping(source = "bill.id" , target = "bill_id"),
            @Mapping(source = "product.id" , target = "product_id")
    })
    PurchaseDto purchaseToPurchaseDTo(Purchase purchase);
}
