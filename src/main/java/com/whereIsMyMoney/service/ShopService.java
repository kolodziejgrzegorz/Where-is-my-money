package com.whereIsMyMoney.service;

import com.whereIsMyMoney.api.mapper.ShopMapper;
import com.whereIsMyMoney.api.model.ShopDto;
import com.whereIsMyMoney.dao.ShopDao;
import com.whereIsMyMoney.domain.Shop;
import com.whereIsMyMoney.exception.DataExistsException;
import com.whereIsMyMoney.exception.DataNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShopService {

    private final ShopDao shopDao;
    private final ShopMapper shopMapper;

    public ShopService(ShopDao shopDao, ShopMapper shopMapper) {
        this.shopDao = shopDao;
        this.shopMapper = shopMapper;
    }

    public List<ShopDto> findAll() {
        List<ShopDto> shopListDto = new ArrayList<>();

        shopListDto = shopDao.findAll().stream()
                .map(shopMapper::shopToShopDto)
                .collect(Collectors.toList());
        if(shopListDto.isEmpty()){
            throw new DataNotFoundException("Shops list not found");
        }
        return shopListDto;
    }

    public ShopDto findById(Long id) {
        Optional<Shop> shopOptional = shopDao.findById(id);
        if(!shopOptional.isPresent()){
            throw new DataNotFoundException("Not found shop with id: " + id);
        }
        shopDao.getOne(id);
        return shopMapper.shopToShopDto(shopOptional.get());
    }

    public ShopDto findByName(String name) {
        Optional<Shop> shopOptional = shopDao.findByName(name);
        if(!shopOptional.isPresent()){
            throw new DataNotFoundException("Not found shop with name: " + name);
        }
        return shopMapper.shopToShopDto(shopOptional.get());
    }

    public ShopDto addNew(ShopDto theShopDto) {
        Optional<Shop> shopOptional = shopDao.findByName(theShopDto.getName());
        if(shopOptional.isPresent()){
            throw new DataExistsException("Shop with name '" + theShopDto.getName() + "' already exists");
        }
        Shop savedShop = shopDao.save(shopMapper.shopDtoToShop(theShopDto));

        return shopMapper.shopToShopDto(savedShop);
    }

    public ShopDto update(ShopDto theShopDto) {
        Optional<Shop> shopOptional = shopDao.findById(theShopDto.getId());
        if(!shopOptional.isPresent()){
            throw new DataNotFoundException("Not found shop with id: " + theShopDto.getId());
        }
        if(shopDao.existsByName(theShopDto.getName())){
            throw new DataExistsException("Shop with name '" + theShopDto.getName() + "' already exists");
        }
        Shop savedShop = shopDao.save(shopMapper.shopDtoToShop(theShopDto));

        return shopMapper.shopToShopDto(savedShop);
    }

    public void delete(Long id) {
        shopDao.deleteById(id);
    }

    public void delete(String name) {
        shopDao.deleteByName(name);
    }
}
