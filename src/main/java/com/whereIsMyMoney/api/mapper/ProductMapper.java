package com.whereIsMyMoney.api.mapper;

import com.whereIsMyMoney.api.model.ProductDto;
import com.whereIsMyMoney.dao.CategoryDao;
import com.whereIsMyMoney.domain.Category;
import com.whereIsMyMoney.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Optional;


@Mapper(componentModel = "spring", uses = {CategoryDao.class})
public abstract class ProductMapper {

    private CategoryDao categoryDao;

    @Mapping(source = "categoryName", target = "category")
    public abstract Product productDtoToProduct(ProductDto productDto);

    @Mapping(source = "category.name", target = "categoryName")
    public abstract ProductDto productToProductDto(Product product);

    public Category categoryNameToCategory(String name){
        if (name == null) {
            return null;
        }
        Optional<Category> category = categoryDao.findByName(name);
        if (!category.isPresent()) {
            return null;
        }
        return category.get();
    }
}
