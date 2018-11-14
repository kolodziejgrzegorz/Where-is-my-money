package com.whereIsMyMoney.service;

import com.whereIsMyMoney.dao.BillDao;
import com.whereIsMyMoney.dao.PurchaseDao;
import com.whereIsMyMoney.dao.ShopDao;
import com.whereIsMyMoney.dao.UserDao;
import com.whereIsMyMoney.exception.DataNotFoundException;
import com.whereIsMyMoney.model.Bill;
import com.whereIsMyMoney.model.Purchase;
import com.whereIsMyMoney.model.Shop;
import com.whereIsMyMoney.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillService {

    private final BillDao billDao;
    private final ShopDao shopDao;
    private final PurchaseDao purchaseDao;
    private final UserDao userDao;

    public BillService(BillDao billDao, ShopDao shopDao, PurchaseDao purchaseDao, UserDao userDao) {
        this.billDao = billDao;
        this.shopDao = shopDao;
        this.purchaseDao = purchaseDao;
        this.userDao = userDao;
    }

    public List<Bill> getAll(){
        return billDao.findAll();
    }

    public Bill getOne(int id){
        Bill theBill = billDao.getOne(id);
        return theBill;
    }

    public List<Bill> getByUserId(int id){
        return billDao.findByUserId(id);
    }

    public Bill save(Bill theBill){
        setShopToBill(theBill);
        return billDao.save(theBill);
    }

    public Bill update(Bill theBill){
//        setUserToBill(theBill);
        setShopToBill(theBill);
//        theBill.setSum(sumCalculator(theBill));
        return billDao.save(theBill);
    }

    public void delete(Bill theBill){
        onDeleteAction(theBill);
        billDao.delete(theBill);
    }
    public void delete(int id){
        onDeleteAction(getOne(id));
        billDao.deleteById(id);
    }
    public boolean exists(int id){
        return billDao.existsById(id);
    }

    private void setShopToBill(Bill theBill){
        String shopName = theBill.getShop().getName();
        if(shopDao.findByName(shopName) == null){
            throw new DataNotFoundException("Not found shop with name " + shopName);
        }
        Shop shop = shopDao.findByName(shopName);
        theBill.setShop(shop);
    }

    private void setUserToBill(Bill theBill){
        String userName = theBill.getUser().getName();
        User user = userDao.findByName(userName);
        theBill.setUser(user);
    }

    private void onDeleteAction(Bill theBill){
        List<Purchase> purchases = purchaseDao.findByBillId(theBill.getId());
        purchaseDao.deleteAll(purchases);
    }

    private int sumCalculator(Bill theBill){
        int sum = 0;
        List<Purchase> purchases = purchaseDao.findByBillId(theBill.getId());
        for(Purchase p : purchases){
            sum += p.getSum();
        }
        return sum;
    }
}
