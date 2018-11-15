package com.whereIsMyMoney.dao;

import com.whereIsMyMoney.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Integer> {
    User findByName(String name);
}
