package com.whereIsMyMoney.service;

import com.whereIsMyMoney.dao.CategoryDao;
import com.whereIsMyMoney.dao.ProductDao;
import com.whereIsMyMoney.domain.Category;
import com.whereIsMyMoney.domain.Product;
import com.whereIsMyMoney.exception.DataNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductDao productDao;
    private final CategoryDao categoryDao;

    public ProductService(ProductDao productDao, CategoryDao categoryDao) {
        this.productDao = productDao;
        this.categoryDao = categoryDao;
    }

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
        productDao.deleteById(id);
    }
    public boolean exists(int id){
        return productDao.existsById(id);
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
