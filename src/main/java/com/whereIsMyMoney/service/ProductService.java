package com.whereIsMyMoney.service;

import com.whereIsMyMoney.api.mapper.ProductMapper;
import com.whereIsMyMoney.api.model.ProductDto;
import com.whereIsMyMoney.dao.ProductDao;
import com.whereIsMyMoney.domain.Product;
import com.whereIsMyMoney.exception.DataExistsException;
import com.whereIsMyMoney.exception.DataNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductDao productDao;
    private final ProductMapper productMapper;

    public ProductService(ProductDao productDao, ProductMapper productMapper) {
        this.productDao = productDao;
        this.productMapper = productMapper;
    }

    public List<ProductDto> findAll(){
        List<ProductDto> productDtoList = new ArrayList<>();
        productDtoList = productDao.findAll()
                .stream()
                .map(productMapper::productToProductDto)
                .collect(Collectors.toList());
        if(productDtoList.isEmpty()){
            throw new DataNotFoundException("Products list not found");
        }
        return productDtoList;
    }

    public ProductDto findById(Long id) {
        Optional<Product> productOptional = productDao.findById(id);
        if(!productOptional.isPresent()){
            throw new DataNotFoundException("Not found product with id: " + id);
        }else {
            return productMapper.productToProductDto(productOptional.get());
        }
    }

    public ProductDto findByName(String name) {
        Optional<Product> productOptional = productDao.findByName(name);
        if(!productOptional.isPresent()){
            throw new DataNotFoundException("Not found product with name: " + name);
        }
        return productMapper.productToProductDto(productOptional.get());
    }

    public ProductDto addNew(ProductDto theProductDto) {
        Optional<Product> productOptional = productDao.findByName(theProductDto.getName());
        if(productOptional.isPresent()){
            throw new DataExistsException("Product with name '" + theProductDto.getName() + "' already exists");
        }
        Product savedProduct = productDao.save(productMapper.productDtoToProduct(theProductDto));

        return productMapper.productToProductDto(savedProduct);
    }

    public ProductDto update(ProductDto theProductDto) {
        Optional<Product> productOptional = productDao.findById(theProductDto.getId());
        if(!productOptional.isPresent()){
            throw new DataNotFoundException("Not found product with id: " + theProductDto.getId());
        }
        if(productDao.existsByName(theProductDto.getName())){
            throw new DataExistsException("Product with name: " + theProductDto.getName() + " already exists");
        }
        Product savedProduct = productDao.save(productMapper.productDtoToProduct(theProductDto));

        return productMapper.productToProductDto(savedProduct);
    }
        
    public void delete(Long id){
        productDao.deleteById(id);
    }

    public void delete(String name){
        productDao.deleteByName(name);
    }

    //todo
    public List<Product> getByNameStartingWith(String name){
        return productDao.findByNameStartingWith(name);
    }
}
