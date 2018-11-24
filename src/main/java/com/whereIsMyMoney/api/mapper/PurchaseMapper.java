package com.whereIsMyMoney.api.mapper;

import com.whereIsMyMoney.api.model.PurchaseDto;
import com.whereIsMyMoney.dao.BillDao;
import com.whereIsMyMoney.domain.Purchase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {BillDao.class})
public interface PurchaseMapper {

    PurchaseMapper INSTANCE = Mappers.getMapper(PurchaseMapper.class);

    @Mappings({
            @Mapping(source = "bill_id" , target = "bill.id"),
            @Mapping(source = "product" , target = "product")
    })
    Purchase purchaseDtoToPurchase(PurchaseDto purchaseDto);

    @Mappings({
            @Mapping(source = "bill.id" , target = "bill_id"),
            @Mapping(source = "product" , target = "product")
    })
    PurchaseDto purchaseToPurchaseDto(Purchase purchase);
}
