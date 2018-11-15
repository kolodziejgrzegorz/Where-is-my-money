package com.whereIsMyMoney.dao;

import com.whereIsMyMoney.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDao extends JpaRepository<Category, Integer> {
    Category findByName(String name);
}