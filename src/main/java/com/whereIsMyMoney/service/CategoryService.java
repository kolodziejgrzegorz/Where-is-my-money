package com.whereIsMyMoney.service;

import com.whereIsMyMoney.dao.CategoryDao;
import com.whereIsMyMoney.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryDao categoryDao;

    public CategoryService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public List<Category> getAll(){
        return categoryDao.findAll();
    }

    public Category getOne(int id){
        return categoryDao.getOne(id);
    }

    public Category add(Category theCategory){
        return categoryDao.save(theCategory);
    }

    public Category update(Category theCategory){
        return categoryDao.save(theCategory);
    }

    public void delete(Category theCategory){
//        onDeleteSetMessage(theCategory);
        categoryDao.delete(theCategory);
    }
    public void delete(int id){
//        onDeleteSetMessage(getOne(id));
        categoryDao.deleteById(id);
    }
    public boolean exists(int id){
        return categoryDao.existsById(id);
    }
    
    private void onDeleteSetMessage(Category theCategory){
        theCategory.setName("Category deleted");
        categoryDao.save(theCategory);
    }
}
