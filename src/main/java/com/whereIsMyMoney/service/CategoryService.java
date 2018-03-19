package com.whereIsMyMoney.service;

import com.whereIsMyMoney.dao.CategoryDao;
import com.whereIsMyMoney.dataModel.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryDao categoryDao;

    public List<Category> getAll(){
        return categoryDao.findAll();
    }

    public Category getOne(int id){
        return categoryDao.getOne(id);
    }

    public void add(Category theCategory){
        categoryDao.save(theCategory);
    }

    public void update(Category theCategory){
        categoryDao.save(theCategory);
    }

    public void delete(Category theCategory){
        onDeleteSetMessage(theCategory);
//        categoryDao.delete(theCategory);
    }
    public void delete(int id){
        onDeleteSetMessage(getOne(id));
//        categoryDao.delete(id);
    }
    public boolean exists(int id){
        return categoryDao.exists(id);
    }
    
    private void onDeleteSetMessage(Category theCategory){
        theCategory.setName("Category deleted");
        categoryDao.save(theCategory);
    }
}
