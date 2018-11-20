package com.whereIsMyMoney.api.mapper;

import com.whereIsMyMoney.api.model.BillDto;
import com.whereIsMyMoney.domain.Bill;
import com.whereIsMyMoney.service.ShopService;
import com.whereIsMyMoney.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {UserService.class, ShopService.class})
public interface BillMapper {

    BillMapper INSTANCE = Mappers.getMapper(BillMapper.class);

    @Mappings({
            @Mapping(source = "user.id",target = "userId"),
            @Mapping(source = "shop.name", target = "shopName")})
    BillDto billToBillDto(Bill bill);

    @Mappings({
            @Mapping(source = "shopName", target = "shop"),
            @Mapping(source = "userId",target = "user")})
    Bill billDtoToBill(BillDto billDto);
}
