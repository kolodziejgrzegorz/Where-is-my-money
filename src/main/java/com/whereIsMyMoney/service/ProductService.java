package com.whereIsMyMoney.service;

import com.whereIsMyMoney.dao.CategoryDao;
import com.whereIsMyMoney.dao.ProductDao;
import com.whereIsMyMoney.dao.PurchaseDao;
import com.whereIsMyMoney.dataModel.Category;
import com.whereIsMyMoney.dataModel.Product;
import com.whereIsMyMoney.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductDao productDao;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    PurchaseDao purchaseDao;

    public List<Product> getAll(){
        return productDao.findAll();
    }

    public Product getOne(int id){
        Product theProduct = productDao.getOne(id);
        setCategoryToProduct(theProduct);
        return theProduct;
    }

    public Product getOne(String  name){
        Product theProduct = productDao.findByName(name);
        setCategoryToProduct(theProduct);
        return theProduct;
    }

    public void add(Product theProduct){
        setCategoryToProduct(theProduct);
        productDao.save(theProduct);
    }

    public void update(Product theProduct){
        setCategoryToProduct(theProduct);
        productDao.save(theProduct);
    }

    public void delete(Product theProduct){
        productDao.delete(theProduct);
    }
    public void delete(int id){
        productDao.delete(id);
    }
    public boolean exists(int id){
        return productDao.exists(id);
    }

    private void setCategoryToProduct(Product theProduct){
        String categoryName = theProduct.getCategory().getName();
        Category category = categoryDao.findByName(categoryName);
        if(category == null){
            throw new DataNotFoundException("Category with name: " + categoryName + " not found" );
        }
        theProduct.setCategory(category);
    }
}
