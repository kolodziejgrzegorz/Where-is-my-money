package com.whereIsMyMoney.api.mapper;

import com.whereIsMyMoney.api.model.BillDto;
import com.whereIsMyMoney.dao.ShopDao;
import com.whereIsMyMoney.dao.UserDao;
import com.whereIsMyMoney.domain.Bill;
import com.whereIsMyMoney.domain.Shop;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Optional;

@Mapper(componentModel = "spring", uses = {ShopDao.class, UserDao.class})
public abstract class BillMapper {

    private UserDao userDao;
    private ShopDao shopDao;

    @Mappings({
            @Mapping(source = "user.id", target = "userId"),
            @Mapping(source = "shop.name", target = "shopName"),
            @Mapping(source = "purchases", target = "purchaseListDto.list")})
    public abstract BillDto billToBillDto(Bill bill);

    @Mappings({
            @Mapping(source = "shopName", target = "shop"),
            @Mapping(source = "userId", target = "user"),
            @Mapping(source = "purchaseListDto.list", target = "purchases")})
    public abstract Bill billDtoToBill(BillDto billDto);

    public Shop shopNameToShop(String name) {
        if (name == null) {
            return null;
        }
        Optional<Shop> shop = shopDao.findByName(name);
        if (!shop.isPresent()) {
            return null;
        }
        return shop.get();
    }
}
