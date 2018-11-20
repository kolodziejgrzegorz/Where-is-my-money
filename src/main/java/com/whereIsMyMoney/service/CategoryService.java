package com.whereIsMyMoney.service;

import com.whereIsMyMoney.dao.CategoryDao;
import com.whereIsMyMoney.domain.Category;
import com.whereIsMyMoney.exception.DataNotFoundException;
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

    public Category getOne(Long id){
        return categoryDao.getOne(id);
    }
    public Category getOne(String name){
        return categoryDao.findByName(name);
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
    public void delete(Long id){
//        onDeleteSetMessage(findById(id));
        categoryDao.deleteById(id);
    }
    public boolean exists(Long id){
        if(!categoryDao.existsById(id)) {
            throw new DataNotFoundException("Category with Id = " + id + " not found ");
        }
        return true;
    }
    
    private void onDeleteSetMessage(Category theCategory){
        theCategory.setName("Category deleted");
        categoryDao.save(theCategory);
    }
}
