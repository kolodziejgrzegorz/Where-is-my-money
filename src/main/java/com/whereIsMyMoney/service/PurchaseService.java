package com.whereIsMyMoney.service;

import com.whereIsMyMoney.dao.PurchaseDao;
import com.whereIsMyMoney.domain.Product;
import com.whereIsMyMoney.domain.Purchase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {

    private final PurchaseDao purchaseDao;
    private final ProductService productService;

    public PurchaseService(PurchaseDao purchaseDao, ProductService productService) {
        this.purchaseDao = purchaseDao;
        this.productService = productService;
    }

    public List<Purchase> getAll(){
        return purchaseDao.findAll();
    }

    public Purchase findById(Long id){
        return purchaseDao.getOne(id);
    }

    public List<Purchase> getByBillId(Long id){
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
    public void delete(List<Purchase> purchases){
        purchaseDao.deleteAll(purchases);
    }
    public void delete(Long id){
        purchaseDao.deleteById(id);
    }

    public boolean exists(Long id){
        return purchaseDao.existsById(id);
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
