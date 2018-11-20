package com.whereIsMyMoney.dao;

import com.whereIsMyMoney.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopDao extends JpaRepository<Shop, Long> {
    Shop findByName(String name);
}
