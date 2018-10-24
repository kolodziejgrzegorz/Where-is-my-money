package com.whereIsMyMoney.dao;

import com.whereIsMyMoney.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillDao extends JpaRepository<Bill, Integer> {
    List<Bill> findByShopName(String name);
    List<Bill> findByUserName(String name);
    List<Bill> findByUserId(int id);
}
