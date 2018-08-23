package com.whereIsMyMoney.service;

import com.whereIsMyMoney.dao.PurchaseDao;
import com.whereIsMyMoney.dataModel.Product;
import com.whereIsMyMoney.dataModel.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {
    @Autowired
    PurchaseDao purchaseDao;

    @Autowired
    ProductService productService;

    public List<Purchase> getAll(){
        return purchaseDao.findAll();
    }

    public Purchase getOne(int id){
        return purchaseDao.getOne(id);
    }

    public List<Purchase> getByBillId(int id){
        return purchaseDao.findByBillId(id);
    }

    public Purchase add(Purchase thePurchase){
        setProductToPurchase(thePurchase);
        thePurchase.setSum( thePurchase.getProductQuantity()*thePurchase.getProductPrice() );
        return purchaseDao.save(thePurchase);
    }

    public Purchase update(Purchase thePurchase){
        setProductToPurchase(thePurchase);
        thePurchase.setSum( thePurchase.getProductQuantity()*thePurchase.getProductPrice() );
        return purchaseDao.save(thePurchase);
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
        Product product = productService.getOne(thePurchase.getProduct().getName());
        if( product == null ){
            product = new Product();
            product.setName(thePurchase.getProduct().getName());
            product.setCategory(thePurchase.getProduct().getCategory());
            productService.add(product);
        }
        thePurchase.setProduct(product);
    }
}
