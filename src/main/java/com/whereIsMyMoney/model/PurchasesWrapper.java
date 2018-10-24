package com.whereIsMyMoney.model;

import java.util.List;

public class PurchasesWrapper {

    private List<Purchase> purchases;

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }
}
