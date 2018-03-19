package com.whereIsMyMoney.dao;

import com.whereIsMyMoney.dataModel.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopDao extends JpaRepository<Shop, Integer> {
    Shop findByName(String name);
}
