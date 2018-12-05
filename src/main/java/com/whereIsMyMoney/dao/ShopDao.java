package com.whereIsMyMoney.dao;

import com.whereIsMyMoney.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopDao extends JpaRepository<Shop, Long> {
    Optional<Shop> findByName(String name);
    boolean existsByName(String name);
    void deleteByName(String name);
}
