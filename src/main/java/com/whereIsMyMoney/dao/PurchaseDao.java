package com.whereIsMyMoney.dao;

import com.whereIsMyMoney.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseDao extends JpaRepository<Purchase, Integer> {
    Purchase findByProductId(int id);
    List<Purchase> findByBillId(int id);
}
