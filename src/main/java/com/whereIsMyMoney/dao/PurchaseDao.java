package com.whereIsMyMoney.dao;

import com.whereIsMyMoney.domain.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseDao extends JpaRepository<Purchase, Long> {
    Purchase findByProductId(Long id);
    List<Purchase> findByBillId(Long id);
}
