package com.whereIsMyMoney.dao;

import com.whereIsMyMoney.dataModel.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {
    Product findByName(String name);
    List<Product> findByCategoryName(String name);
    List<Product> findByNameStartingWith(String name);
}
