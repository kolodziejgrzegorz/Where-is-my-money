package com.whereIsMyMoney.dao;

import com.whereIsMyMoney.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDao extends JpaRepository<Category, Integer> {
    Category findByName(String name);
}