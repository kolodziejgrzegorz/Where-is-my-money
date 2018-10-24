package com.whereIsMyMoney.service;

import com.whereIsMyMoney.dao.CategoryDao;
import com.whereIsMyMoney.dao.ProductDao;
import com.whereIsMyMoney.exception.DataNotFoundException;
import com.whereIsMyMoney.model.Category;
import com.whereIsMyMoney.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductDao productDao;

    @Autowired
    CategoryDao categoryDao;

    public List<Product> getAll(){
        return productDao.findAll();
    }

    public Product getOne(int id){
        Product theProduct = productDao.getOne(id);
        if(theProduct != null) {
            setCategoryToProduct(theProduct);
        }
        return theProduct;
    }

    public Product getOne(String  name){
        Product theProduct = productDao.findByName(name);
        if(theProduct != null) {
            setCategoryToProduct(theProduct);
        }
        return theProduct;
    }

    public Product add(Product theProduct){
        setCategoryToProduct(theProduct);
        return productDao.save(theProduct);
    }

    public Product update(Product theProduct){
        setCategoryToProduct(theProduct);
        return productDao.save(theProduct);
    }

    public List<Product> getByNameStartingWith(String name){
        return productDao.findByNameStartingWith(name);
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
