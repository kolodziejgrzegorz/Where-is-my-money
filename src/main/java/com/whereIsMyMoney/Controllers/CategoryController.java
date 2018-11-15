package com.whereIsMyMoney.Controllers;

import com.whereIsMyMoney.domain.Category;
import com.whereIsMyMoney.exception.DataNotFoundException;
import com.whereIsMyMoney.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    @ResponseStatus(HttpStatus.OK)
    public List<Category> getAllCategorys() {
        List<Category> categories = categoryService.getAll();
        if(categories==null) {
            throw new DataNotFoundException("Category list not found");
        }
        return categories;
    }

    @GetMapping(value = "/categories/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public Category getCategory(@PathVariable("id") int id) {
        categoryExist(id);
        return categoryService.getOne(id);
    }

    @RequestMapping( value = "/categories", method = RequestMethod.POST )
    @ResponseStatus(HttpStatus.CREATED)
    public Category add(@RequestBody Category theCategory) {
        if(categoryService.exists(theCategory.getId())) {
            throw new  DataNotFoundException("Category with Id = " + theCategory.getId() + " already exists");
        }
       return categoryService.add(theCategory);
    }

    @PutMapping( "/categories/{id}" )
    @ResponseStatus(HttpStatus.OK)
    public Category updateCategory(@RequestBody Category theCategory) {
        categoryExist(theCategory.getId());
        return categoryService.update(theCategory);
    }

    @DeleteMapping("/categories/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCategory(@PathVariable("id") int id) {
        categoryExist(id);
        categoryService.delete(id);
    }
    private void categoryExist(int id){
        if(!categoryService.exists(id)) {
            throw new  DataNotFoundException("Category with Id = " + id + " not found ");
        }
    }

}
