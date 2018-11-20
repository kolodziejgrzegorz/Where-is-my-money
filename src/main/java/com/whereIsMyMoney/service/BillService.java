package com.whereIsMyMoney.service;

import com.whereIsMyMoney.dao.BillDao;
import com.whereIsMyMoney.domain.Bill;
import com.whereIsMyMoney.domain.Purchase;
import com.whereIsMyMoney.domain.Shop;
import com.whereIsMyMoney.domain.User;
import com.whereIsMyMoney.exception.DataExistsException;
import com.whereIsMyMoney.exception.DataNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillService {

    private final BillDao billDao;
    private final ShopService shopService;
    private final PurchaseService purchaseService;
    private final UserService userService;

    public BillService(BillDao billDao, ShopService shopService,
                       PurchaseService purchaseService, UserService userService) {
        this.billDao = billDao;
        this.shopService = shopService;
        this.purchaseService = purchaseService;
        this.userService = userService;
    }

    public List<Bill> getAll(){
        List<Bill> bills = billDao.findAll();
        if( bills.isEmpty() ) {
            throw new DataNotFoundException("Bills list not found");
        }
        return bills;
    }

    public Bill getOne(Long id){
        exists(id);
        Bill theBill = billDao.getOne(id);
        return theBill;
    }

    public List<Bill> getByUserId(Long id){
        List<Bill> bills = billDao.findByUserId(id);
        if( bills.isEmpty() ) {
            throw new DataNotFoundException("Bills list not found");
        }
        return bills;
    }

    public Bill addNew(Bill theBill){
        if(exists(theBill.getId())) {
            throw new DataExistsException("Bill with id '" + theBill.getId() + "' already exists");
        }else {
            setShopToBill(theBill);
            return billDao.save(theBill);
        }
    }

    public Bill update(Long id, Bill theBill){
        exists(id);
        theBill.setId(id);
//        setUserToBill(theBill);
        setShopToBill(theBill);
//        theBill.setSum(sumCalculator(theBill));
        return billDao.save(theBill);
    }

    public void delete(Bill theBill){
        deletePurchasesByBillId(theBill);
        billDao.delete(theBill);
    }
    public void delete(Long id){
//      when delete bill delete also all his purchases
        deletePurchasesByBillId(getOne(id));
        billDao.deleteById(id);
    }
    public boolean exists(Long id){
        if(!billDao.existsById(id)) {
            throw new  DataNotFoundException("Bill with Id = " + id + " not found ");
        }
        return true;
    }

    private void setShopToBill(Bill theBill){
        String shopName = theBill.getShop().getName();
        if(shopService.findByName(shopName) == null){
            throw new DataNotFoundException("Not found shop with name " + shopName);
        }
        Shop shop = shopService.findByName(shopName);
        theBill.setShop(shop);
    }

    private void setUserToBill(Bill theBill){
        String userName = theBill.getUser().getName();
        User user = userService.findByName(userName);
        theBill.setUser(user);
    }

    private void deletePurchasesByBillId(Bill theBill){
        List<Purchase> purchases = purchaseService.getByBillId(theBill.getId());
        purchaseService.delete(purchases);
    }

    private Double sumCalculator(Bill theBill){
        Double sum = 0.0;
        List<Purchase> purchases = purchaseService.getByBillId(theBill.getId());
        for(Purchase p : purchases){
            sum += p.getSum();
        }
        return sum;
    }
}
