package com.whereIsMyMoney.dao;

import com.whereIsMyMoney.domain.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillDao extends JpaRepository<Bill, Integer> {
    List<Bill> findByShopName(String name);
    List<Bill> findByUserName(String name);
    List<Bill> findByUserId(int id);
}
