package com.whereIsMyMoney.service;

import com.whereIsMyMoney.dao.ProductDao;
import com.whereIsMyMoney.dao.PurchaseDao;
import com.whereIsMyMoney.dataModel.Product;
import com.whereIsMyMoney.dataModel.Purchase;
import com.whereIsMyMoney.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {
    @Autowired
    PurchaseDao purchaseDao;
    
    @Autowired
    ProductDao productDao;

    public List<Purchase> getAll(){
        return purchaseDao.findAll();
    }

    public Purchase getOne(int id){
        return purchaseDao.getOne(id);
    }

    public List<Purchase> getByBillId(int id){
        return purchaseDao.findByBillId(id);
    }

    public void add(Purchase thePurchase){
        setProductToPurchase(thePurchase);
        thePurchase.setSum( thePurchase.getProduct_quantity()*thePurchase.getProduct_price() );
        purchaseDao.save(thePurchase);
    }

    public void update(Purchase thePurchase){
        setProductToPurchase(thePurchase);
        thePurchase.setSum( thePurchase.getProduct_quantity()*thePurchase.getProduct_price() );
        purchaseDao.save(thePurchase);
    }

    public void delete(Purchase thePurchase){
        purchaseDao.delete(thePurchase);
    }
    public void delete(int id){
        purchaseDao.delete(id);
    }
    public boolean exists(int id){
        return purchaseDao.exists(id);
    }

    private void setProductToPurchase(Purchase thePurchase){
        String productName = thePurchase.getProduct().getName();
        if(productDao.findByName(productName) == null){
            throw new DataNotFoundException("Not found product with name " + productName);
        }
        Product product = productDao.findByName(productName);
        thePurchase.setProduct(product);
    }
}
