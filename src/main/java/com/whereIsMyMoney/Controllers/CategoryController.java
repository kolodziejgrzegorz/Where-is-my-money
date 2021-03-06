package com.whereIsMyMoney.Controllers;

import com.whereIsMyMoney.api.model.CategoryDto;
import com.whereIsMyMoney.service.CategoryService;
import org.springframework.http.HttpStatus;
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
    public List<CategoryDto> getAllCategories() {
        return categoryService.findAll();
    }

    @GetMapping("/categories/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto getCategory(@PathVariable("id") Long id) {
        return categoryService.findById(id);
    }

    @PostMapping("/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto addNew(@RequestBody CategoryDto theCategory) {
       return categoryService.addNew(theCategory);
    }

    @PutMapping( "/categories/{id}" )
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto updateCategory(@RequestBody CategoryDto theCategory) {
        return categoryService.update(theCategory);
    }

    @DeleteMapping("/categories/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCategory(@PathVariable("id") Long id) {
        categoryService.delete(id);
    }
}
