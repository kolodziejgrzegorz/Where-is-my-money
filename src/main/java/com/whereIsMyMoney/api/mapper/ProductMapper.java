package com.whereIsMyMoney.api.mapper;

import com.whereIsMyMoney.api.model.ProductDto;
import com.whereIsMyMoney.domain.Product;
import com.whereIsMyMoney.service.CategoryService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", uses = {CategoryService.class})
public interface ProductMapper {

    ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "categoryName", target = "category")
    Product productDtoToProduct(ProductDto productDto);

    @Mapping(source = "category.name", target = "categoryName")
    ProductDto productToProductDto(Product product);
}
