package com.whereIsMyMoney.service;

import com.whereIsMyMoney.api.mapper.CategoryMapper;
import com.whereIsMyMoney.api.model.CategoryDto;
import com.whereIsMyMoney.dao.CategoryDao;
import com.whereIsMyMoney.domain.Category;
import com.whereIsMyMoney.exception.DataExistsException;
import com.whereIsMyMoney.exception.DataNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryDao categoryDao;
    private final CategoryMapper categoryMapper;
 
    public CategoryService(CategoryDao categoryDao, CategoryMapper categoryMapper) {
        this.categoryDao = categoryDao;
        this.categoryMapper = categoryMapper;
    }

    public List<CategoryDto> findAll(){
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        
        categoryDtoList = categoryDao.findAll()
                .stream()
                .map(categoryMapper::categoryToCategoryDto)
                .collect(Collectors.toList());
        if(categoryDtoList.isEmpty()){
            throw new DataNotFoundException("Category List Not Found");
        }
        return categoryDtoList;
    }

    public CategoryDto findById(Long id){
        Optional<Category> categoryOptional = categoryDao.findById(id);
        if(!categoryOptional.isPresent()){
            throw new DataNotFoundException("Not Found category with id: " + id);
        }
        return categoryMapper.categoryToCategoryDto(categoryOptional.get());
    }
    public CategoryDto findById(String name){
        Optional<Category> categoryOptional = categoryDao.findByName(name);
        if(!categoryOptional.isPresent()){
            throw new DataNotFoundException("Not Found category with name: " + name);
        }
        return categoryMapper.categoryToCategoryDto(categoryOptional.get());
    }

    public CategoryDto addNew(CategoryDto theCategoryDto){
        Optional<Category> categoryOptional = categoryDao.findByName(theCategoryDto.getName());
        if(categoryOptional.isPresent()){
            throw new DataExistsException("Category with name '" + theCategoryDto.getName() + "' already exists");
        }
        Category savedCategory = categoryDao.save(categoryMapper.categoryDtoToCategory(theCategoryDto));

        return categoryMapper.categoryToCategoryDto(savedCategory);
    }

    public CategoryDto update(CategoryDto theCategoryDto){
        Optional<Category> categoryOptional = categoryDao.findByName(theCategoryDto.getName());
        if(!categoryOptional.isPresent()){
            throw new DataNotFoundException("Not found category with name: " + theCategoryDto.getName());
        }
        Category savedCategory = categoryDao.save(categoryMapper.categoryDtoToCategory(theCategoryDto));

        return categoryMapper.categoryToCategoryDto(savedCategory);
    }

    public void delete(Long id){
        //todo
//        onDeleteSetMessage(findById(id));
        categoryDao.deleteById(id);
    }
    
    private void onDeleteSetMessage(Category theCategory){
        theCategory.setName("Category deleted");
        categoryDao.save(theCategory);
    }
}
