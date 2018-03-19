package com.whereIsMyMoney.dao;

import com.whereIsMyMoney.dataModel.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
    User findByName(String name);
}
