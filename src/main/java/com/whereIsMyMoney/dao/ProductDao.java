package com.whereIsMyMoney.dao;

import com.whereIsMyMoney.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, Long> {
    Product findByName(String name);
    List<Product> findByCategoryName(String name);
    List<Product> findByNameStartingWith(String name);
}
