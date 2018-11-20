package com.whereIsMyMoney.api.mapper;

import com.whereIsMyMoney.api.model.ShopDto;
import com.whereIsMyMoney.domain.Shop;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ShopMapper {

    ShopMapper shopMapper = Mappers.getMapper(ShopMapper.class);

    Shop shopDtoToShop(ShopDto shopDto);

    ShopDto shopToShopDto(Shop shop);
}
