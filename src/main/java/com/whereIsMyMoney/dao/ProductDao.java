package com.whereIsMyMoney.dao;

import com.whereIsMyMoney.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, Integer> {
    Product findByName(String name);
    List<Product> findByCategoryName(String name);
    List<Product> findByNameStartingWith(String name);
}
