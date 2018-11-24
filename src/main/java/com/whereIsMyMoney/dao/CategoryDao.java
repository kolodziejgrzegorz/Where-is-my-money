package com.whereIsMyMoney.dao;

import com.whereIsMyMoney.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryDao extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}